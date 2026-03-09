package com.forum.forumhub.user.repository;

import com.forum.forumhub.user.entity.UserModel;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(@Email String email);

    Optional<UserModel> findByIdAndActiveTrue(UUID id);
}
