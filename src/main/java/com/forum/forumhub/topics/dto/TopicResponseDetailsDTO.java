package com.forum.forumhub.topics.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicResponseDetailsDTO(
        UUID id,
        String title,
        String message,
        boolean active,
        String forumName,
        String authorName,
        LocalDateTime createdAt
) {}