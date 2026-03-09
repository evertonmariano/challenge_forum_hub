package com.forum.forumhub.user.dto.auth;

import com.forum.forumhub.user.dto.user.UserResponseDTO;

public record LoginResponseDTO (
        String accessToken,
        UserResponseDTO user
) {}
