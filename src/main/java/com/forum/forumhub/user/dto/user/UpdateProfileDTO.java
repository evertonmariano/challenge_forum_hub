package com.forum.forumhub.user.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record  UpdateProfileDTO(
        @Size(max = 150)
        String name,

        @Email
        String email,

        @Pattern(regexp = "^\\d{10,15}$", message = "Telefone deve conter entre 10 e 15 dÃ­gitos numÃ©ricos")
        String phone,

        @Size(min = 8)
        String password
) {}