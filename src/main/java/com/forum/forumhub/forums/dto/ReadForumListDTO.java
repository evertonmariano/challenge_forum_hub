package com.forum.forumhub.forums.dto;

import com.forum.forumhub.shared.utils.enums.ForumType;

import java.util.UUID;

public record ReadForumListDTO(
        UUID id,
        String name,
        ForumType type
) {}