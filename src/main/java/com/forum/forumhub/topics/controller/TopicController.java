package com.forum.forumhub.topics.controller;

import com.forum.forumhub.security.principal.UserPrincipal;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.topics.dto.CreateTopicDTO;
import com.forum.forumhub.topics.dto.TopicListResponseDTO;
import com.forum.forumhub.topics.dto.TopicResponseDetailsDTO;
import com.forum.forumhub.topics.dto.UpdateTopicDTO;
import com.forum.forumhub.topics.entity.TopicModel;
import com.forum.forumhub.topics.service.TopicService;
import com.forum.forumhub.user.entity.UserModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // =========================
    // CREATE
    // =========================

    @PostMapping("/{forumId}")
    public ResponseMessage createTopic(
            @PathVariable UUID forumId,
            @RequestBody @Valid CreateTopicDTO data,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return topicService.createTopic(forumId, data, user.getUser());
    }

    // =========================
    // UPDATE
    // =========================

    @PatchMapping("/{id}")
    public ResponseMessage updateTopic(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTopicDTO data,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return topicService.updateTopic(id, data, user.getUser());
    }

    // =========================
    // CLOSE
    // =========================

    @PatchMapping("/{id}/close")
    public ResponseMessage closeTopic(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return topicService.closeTopic(id, user.getUser());
    }

    // =========================
    // DELETE (SOFT)
    // =========================

    @DeleteMapping("/{id}")
    public ResponseMessage deleteTopic(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return topicService.deleteTopic(id, user.getUser());
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/info/{id}")
    public TopicResponseDetailsDTO getTopicById(@PathVariable UUID id) {
        return topicService.getById(id);
    }

    // =========================
    // LIST BY FORUM (PAGINATED)
    // =========================

    @GetMapping("/all")
    public ResponseEntity<List<TopicListResponseDTO>> getAll() {
        return ResponseEntity.ok(topicService.getAll());
    }
}