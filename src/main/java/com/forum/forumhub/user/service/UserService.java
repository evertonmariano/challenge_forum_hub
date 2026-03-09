package com.forum.forumhub.user.service;

import com.forum.forumhub.shared.exceptions.user.EmailAlreadyInUseException;
import com.forum.forumhub.shared.exceptions.user.UserNotFoundException;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.user.dto.user.UpdateProfileDTO;
import com.forum.forumhub.user.dto.user.UserInfoResponseDTO;
import com.forum.forumhub.user.entity.UserModel;
import com.forum.forumhub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfoResponseDTO getProfile(UUID userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserInfoResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Transactional
    public ResponseMessage updateProfile(UUID userId, UpdateProfileDTO dataUser) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (dataUser.name() != null) user.setName(dataUser.name());

        if (dataUser.email() != null) {
            if (userRepository.existsByEmail(dataUser.email())) {
                throw new EmailAlreadyInUseException();
            }
            user.setEmail(dataUser.email());
        }

        if (dataUser.phone() != null) user.setPhone(dataUser.phone());
        if (dataUser.password() != null) {
            user.setPassword(passwordEncoder.encode(dataUser.password()));
        }

        userRepository.save(user);

        return new ResponseMessage("Profile updated successfully");
    }

    public ResponseMessage deactiveAccount(UUID userId) {

        UserModel user = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(UserNotFoundException::new);

        user.deactivate();
        userRepository.save(user);

        return new ResponseMessage("Account successfully deactivated");
    }
}
