package com.forum.forumhub.forums.service;

import com.forum.forumhub.forums.dto.CreateForumDTO;
import com.forum.forumhub.forums.dto.UpdateForumDTO;
import com.forum.forumhub.forums.entity.ForumsModel;
import com.forum.forumhub.forums.repository.ForumsRepository;
import com.forum.forumhub.shared.exceptions.ForbiddenException;
import com.forum.forumhub.shared.exceptions.forums.ForumAlreadyExistsException;
import com.forum.forumhub.shared.exceptions.forums.ForumNotFoundException;
import com.forum.forumhub.shared.exceptions.forums.ForumUpdateForbiddenException;
import com.forum.forumhub.shared.exceptions.forums.PermissionDeniedForumException;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.shared.utils.enums.ForumType;
import com.forum.forumhub.shared.utils.enums.UserRoles;
import com.forum.forumhub.user.entity.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ForumsService {

    private final ForumsRepository repository;
    int limitStudent = 1;


    public ForumsService(
            ForumsRepository repository
    ){
        this.repository = repository;
    }

    // ========= CREATE FORUM =========
    @Transactional
    public ResponseMessage createForum(
            CreateForumDTO forumData,
            UserModel user
    ) {

        ForumType type = forumData.type();
        UserRoles role = user.getRole();

        if (type == ForumType.COURSE_GENERAL || type == ForumType.COURSE_QA) {
            if (role != UserRoles.ROLE_ADMIN && role != UserRoles.ROLE_INSTRUCTOR) {
                throw new PermissionDeniedForumException(
                        "You do not have permission to create a " + type + " forum."
                );
            }
        }
        if ((type == ForumType.OPEN_DISCUSSION || type == ForumType.OPEN_QUESTION)
                && role == UserRoles.ROLE_STUDENT) {

            long count = repository.countByCreatedByAndType(user, type);

            if (count >= limitStudent) {
                throw new ForumAlreadyExistsException(
                        "You already have reached the limit of " + limitStudent + " "
                                + type + " forum(s)."
                );
            }
        }

        ForumsModel forum = new ForumsModel(
                forumData.name(),
                forumData.description(),
                type,
                user
        );

        repository.save(forum);

        return new ResponseMessage("Forum created successfully.");
    }
    // ========= READS FORUM =========
    public Page<ForumsModel> getOpenForums(Pageable pageable) {

        List<ForumType> openTypes = Arrays.stream(ForumType.values())
                .filter(ForumType::isOpen)
                .toList();

        return repository.findByTypeInAndActiveTrue(openTypes, pageable);
    }

    public Page<ForumsModel> getOpenForumsByCategory(
            String category,
            Pageable pageable
    ) {

        List<ForumType> filtered = Arrays.stream(ForumType.values())
                .filter(ForumType::isOpen)
                .filter(type ->
                        category.equalsIgnoreCase("DISCUSSION")
                                ? type.isDiscussion()
                                : type.isQuestion()
                )
                .toList();

        return repository.findByTypeInAndActiveTrue(filtered, pageable);
    }

    public Page<ForumsModel> getMyForums(UserModel user, Pageable pageable) {
        return repository.findByCreatedBy_IdAndActiveTrue(user.getId(), pageable);
    }

    public ForumsModel getForumById(UUID id, UserModel user) {

        ForumsModel forum = repository.findById(id)
                .orElseThrow(() -> new ForumNotFoundException(id.toString()));

        if (!forum.isActive()) {
            throw new ForumNotFoundException(id.toString());
        }

        if (forum.getType().isOpen()) {
            return forum;
        }

        throw new ForbiddenException();
    }
    // ========= UPDATE FORUM =========
    @Transactional
    public ResponseMessage updateForum(UUID forumId, UpdateForumDTO data, UserModel user) {
        ForumsModel forum = repository.findById(forumId)
                .orElseThrow(() -> new ForumNotFoundException(String.valueOf(forumId)));

        if ((user.getRole() == UserRoles.ROLE_STUDENT || user.getRole() == UserRoles.ROLE_INSTRUCTOR)
                && !forum.getCreatedBy().equals(user)) {
            throw new ForumUpdateForbiddenException();
        }

        if (data.name() != null) forum.setName(data.name());
        if (data.description() != null) forum.setDescription(data.description());

        repository.save(forum);

        return new ResponseMessage(
                String.format("Forum '%s' updated successfully by user '%s'.", forum.getName(), user.getName())
        );
    }

    // ========= DELETE FORUM =========
    @Transactional
    public ResponseMessage softDeleteForum(UUID id, UserModel user) {

        ForumsModel forum = repository.findById(id)
                .orElseThrow(() -> new ForumNotFoundException(id.toString()));

        if (!forum.isActive()) {
            throw new ForumNotFoundException(id.toString());
        }

        if (!forum.getCreatedBy().getId().equals(user.getId())) {
            throw new ForbiddenException();
        }

        forum.deactivate();

        return new ResponseMessage("Forum successfully deactivated");
    }
}
