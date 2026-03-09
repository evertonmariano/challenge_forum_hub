package com.forum.forumhub.topics.repository;

import com.forum.forumhub.topics.entity.TopicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<TopicModel, UUID> {

    Optional<TopicModel> findByIdAndActiveTrue(UUID id);

    Page<TopicModel> findAllByForumIdAndActiveTrue(UUID forumId, Pageable pageable);
}
