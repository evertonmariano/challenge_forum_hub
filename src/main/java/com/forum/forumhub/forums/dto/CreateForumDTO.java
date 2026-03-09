package com.forum.forumhub.forums.dto;

import com.forum.forumhub.shared.utils.enums.ForumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateForumDTO (
        @NotBlank
        @Size(max = 120)
        String name,
        @NotBlank
        @Size(max = 500)
        String description,
        @NotNull
        ForumType type
){
}