package com.forum.forumhub.health.dto;

import java.time.LocalDateTime;

public record ApiStatusResponse(
        String status,
        String message,
        LocalDateTime timestamp
) {}
