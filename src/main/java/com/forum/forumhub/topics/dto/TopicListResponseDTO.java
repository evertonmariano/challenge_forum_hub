package com.forum.forumhub.topics.dto;

import java.util.UUID;

public record TopicListResponseDTO(
        UUID id,
        String title,
        String forumName,
        String authorName,
        boolean active
) {}