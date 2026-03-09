package com.forum.forumhub.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @Email
        String email,
        @Size(min = 8)
        String password
) {
}
