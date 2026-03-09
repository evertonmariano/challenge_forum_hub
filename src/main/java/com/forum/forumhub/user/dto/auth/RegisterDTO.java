package com.forum.forumhub.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO (
        @NotBlank
        @Size(max = 150)
        String name,
        @NotBlank
        @Email
        String email,
        @Pattern(
                regexp = "^\\d{10,15}$",
                message = "Telefone deve conter entre 10 e 15 dÃ­gitos numÃ©ricos"
        )
        String phone,
        @NotBlank
        @Size(min = 8)
        String password
) {
}