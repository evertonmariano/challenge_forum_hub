package com.forum.forumhub.user.dto.user;

import com.forum.forumhub.shared.utils.enums.UserRoles;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        UserRoles role,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}