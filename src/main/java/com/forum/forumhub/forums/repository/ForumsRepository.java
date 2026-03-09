package com.forum.forumhub.forums.repository;

import com.forum.forumhub.forums.entity.ForumsModel;
import com.forum.forumhub.shared.utils.enums.ForumType;
import com.forum.forumhub.user.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForumsRepository extends JpaRepository<ForumsModel, UUID> {
    boolean existsByCreatedByAndType(UserModel user, ForumType forumType);
    long countByCreatedByAndType(UserModel createdBy, ForumType type);

    Optional<ForumsModel> findById(UUID id);

    Page<ForumsModel> findByTypeInAndActiveTrue(
            List<ForumType> types,
            Pageable pageable
    );

    Page<ForumsModel> findByCreatedBy_IdAndActiveTrue(UUID createdById, Pageable pageable);
}