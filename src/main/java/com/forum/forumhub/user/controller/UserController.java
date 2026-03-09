package com.forum.forumhub.user.controller;

import com.forum.forumhub.security.principal.UserPrincipal;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.user.dto.user.UpdateProfileDTO;
import com.forum.forumhub.user.dto.user.UserInfoResponseDTO;
import com.forum.forumhub.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoResponseDTO> getProfile(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        UserInfoResponseDTO profile = service.getProfile(principal.getUser().getId());
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/profile")
    public ResponseEntity<ResponseMessage> updateProfileUser(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdateProfileDTO dataUser
    ) {
        return ResponseEntity.ok(
                service.updateProfile(principal.getUser().getId(), dataUser)
        );
    }

    @DeleteMapping("/profile")
    public ResponseEntity<ResponseMessage> deactivateAccountUser(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(
                service.deactiveAccount(principal.getUser().getId())
        );
    }

}