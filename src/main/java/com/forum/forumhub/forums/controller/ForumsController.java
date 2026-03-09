package com.forum.forumhub.forums.controller;

import com.forum.forumhub.forums.dto.CreateForumDTO;
import com.forum.forumhub.forums.dto.ReadForumDetailDTO;
import com.forum.forumhub.forums.dto.ReadForumListDTO;
import com.forum.forumhub.forums.dto.UpdateForumDTO;
import com.forum.forumhub.forums.entity.ForumsModel;
import com.forum.forumhub.forums.service.ForumsService;
import com.forum.forumhub.security.principal.UserPrincipal;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.user.entity.UserModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/forums")
public class ForumsController {

    private final ForumsService forumService;

    public ForumsController(ForumsService forumService){
        this.forumService = forumService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createForum(
            @RequestBody @Valid CreateForumDTO forumData,
            @AuthenticationPrincipal UserPrincipal principal
    ) {

        ResponseMessage response =
                forumService.createForum(forumData, principal.getUser());

        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/update/{forumId}")
    public ResponseEntity<ResponseMessage> updateForum(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdateForumDTO dataForum,
            @PathVariable UUID forumId
    ){
        return ResponseEntity.ok(
                forumService.updateForum(
                        forumId,
                        dataForum,
                        principal.getUser()
                )
        );
    }

    @GetMapping
    public Page<ReadForumListDTO> listOpenForums(
            @RequestParam(required = false) String category,
            Pageable pageable
    ) {

        Page<ForumsModel> forums = category == null
                ? forumService.getOpenForums(pageable)
                : forumService.getOpenForumsByCategory(category, pageable);

        return forums.map(f ->
                new ReadForumListDTO(
                        f.getId(),
                        f.getName(),
                        f.getType()
                )
        );
    }

    @GetMapping("/my")
    public Page<ReadForumListDTO> getMyForums(
            @AuthenticationPrincipal UserPrincipal user,
            Pageable pageable
    ) {

        return forumService.getMyForums(user.getUser(), pageable)
                .map(f ->
                        new ReadForumListDTO(
                                f.getId(),
                                f.getName(),
                                f.getType()
                        ));
    }

    @GetMapping("/{id}")
    public ReadForumDetailDTO getForumById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserModel user
    ) {

        ForumsModel forum = forumService.getForumById(id, user);

        return new ReadForumDetailDTO(
                forum.getId(),
                forum.getName(),
                forum.getDescription(),
                forum.getType(),
                forum.getCreatedBy().getId(),
                forum.isActive()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deactivateForum(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(
                forumService.softDeleteForum(id, principal.getUser())
        );
    }
}