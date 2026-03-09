package com.forum.forumhub.topics.service;

import com.forum.forumhub.forums.entity.ForumsModel;
import com.forum.forumhub.forums.repository.ForumsRepository;
import com.forum.forumhub.shared.exceptions.account.UnauthorizedActionException;
import com.forum.forumhub.shared.exceptions.forums.ForumIsInactiveException;
import com.forum.forumhub.shared.exceptions.forums.ForumNotFoundException;
import com.forum.forumhub.shared.exceptions.topics.ClosedTopicException;
import com.forum.forumhub.shared.exceptions.topics.TopicNotFoundException;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.shared.utils.enums.TopicStatus;
import com.forum.forumhub.topics.dto.CreateTopicDTO;
import com.forum.forumhub.topics.dto.TopicListResponseDTO;
import com.forum.forumhub.topics.dto.TopicResponseDetailsDTO;
import com.forum.forumhub.topics.dto.UpdateTopicDTO;
import com.forum.forumhub.topics.entity.TopicModel;
import com.forum.forumhub.topics.repository.TopicRepository;
import com.forum.forumhub.user.entity.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ForumsRepository forumsRepository;

    public TopicService(
            TopicRepository topicRepository,
            ForumsRepository forumsRepository
    ) {
        this.topicRepository = topicRepository;
        this.forumsRepository = forumsRepository;
    }

    // =========================
    // CREATE
    // =========================

    @Transactional
    public ResponseMessage createTopic(UUID forumId, CreateTopicDTO data, UserModel user) {

        ForumsModel forum = forumsRepository.findById(forumId)
                .orElseThrow(() -> new ForumNotFoundException("Forum not found."));

        if (!forum.isActive()) {
            throw new ForumIsInactiveException("Forum is inactive.");
        }

        TopicModel topic = new TopicModel(
                data.title(),
                data.content(),
                data.type(),
                forum,
                user
        );

        topicRepository.save(topic);

        return new ResponseMessage("Topic created successfully.");
    }

    // =========================
    // UPDATE
    // =========================

    @Transactional
    public ResponseMessage updateTopic(UUID topicId, UpdateTopicDTO data, UserModel user) {

        TopicModel topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found."));

        validateAuthor(topic, user);

        if (topic.getStatus() == TopicStatus.CLOSED) {
            throw new ClosedTopicException("Closed topics cannot be edited.");
        }

        if (data.title() != null) {
            topic.setTitle(data.title());
        }

        if (data.content() != null) {
            topic.setContent(data.content());
        }

        topic.setUpdatedAt(LocalDateTime.now());

        return new ResponseMessage("Topic updated successfully.");
    }

    // =========================
    // CLOSE
    // =========================

    @Transactional
    public ResponseMessage closeTopic(UUID topicId, UserModel user) {

        TopicModel topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found."));

        validateAuthor(topic, user);

        if (topic.getStatus() == TopicStatus.CLOSED) {
            throw new ClosedTopicException("Topic already closed.");
        }

        topic.setStatus(TopicStatus.CLOSED);
        topic.setUpdatedAt(LocalDateTime.now());

        return new ResponseMessage("Topic closed successfully.");
    }

    // =========================
    // SOFT DELETE
    // =========================

    @Transactional
    public ResponseMessage deleteTopic(UUID topicId, UserModel user) {

        TopicModel topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found."));

        validateAuthor(topic, user);

        topic.setActive(false);

        return new ResponseMessage("Topic deleted successfully.");
    }

    // =========================
    // GET BY ID
    // =========================

    public TopicResponseDetailsDTO getById(UUID id) {
        TopicModel topic = topicRepository.findById(id)
                .orElseThrow();

        return new TopicResponseDetailsDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getContent(),
                topic.isActive(),
                topic.getForum().getName(),
                topic.getAuthor().getName(),
                topic.getCreatedAt()
        );
    }


    public List<TopicListResponseDTO> getAll() {
        return topicRepository.findAll()
                .stream()
                .map(topic -> new TopicListResponseDTO(
                        topic.getId(),
                        topic.getTitle(),
                        topic.getForum().getName(),
                        topic.getAuthor().getName(),
                        topic.isActive()
                ))
                .toList();
    }

    // =========================
    // PRIVATE VALIDATIONS
    // =========================

    private void validateAuthor(TopicModel topic, UserModel user) {
        if (!topic.getAuthor().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You are not allowed to perform this action.");
        }
    }
}
