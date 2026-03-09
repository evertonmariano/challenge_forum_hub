package com.forum.forumhub.topics.dto;

import com.forum.forumhub.shared.utils.enums.TopicType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTopicDTO(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 150)
        String title,

        @NotBlank(message = "Content is required")
        @Size(min = 10)
        String content,

        @NotNull(message = "Topic type is required")
        TopicType type
) {}
