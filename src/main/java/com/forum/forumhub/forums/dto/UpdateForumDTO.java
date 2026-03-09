package com.forum.forumhub.forums.dto;

import jakarta.validation.constraints.Size;

public record UpdateForumDTO(
        @Size(max = 120)
        String name,
        @Size(max = 500)
        String description
) {
}
