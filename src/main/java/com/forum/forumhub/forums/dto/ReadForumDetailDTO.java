package com.forum.forumhub.forums.dto;

import com.forum.forumhub.shared.utils.enums.ForumType;

import java.util.UUID;

public record ReadForumDetailDTO(
        UUID id,
        String name,
        String description,
        ForumType type,
        UUID createdById,
        boolean active
) {}