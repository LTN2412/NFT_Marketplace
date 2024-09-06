package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.CommentRequest;
import com.nftmarketplace.user_service.model.dto.response.UserComment;
import com.nftmarketplace.user_service.model.node.Comment;
import com.nftmarketplace.user_service.service.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
        CommentService commentService;

        @PostMapping
        public Mono<APIResponse<?>> createComment(@AuthenticationPrincipal Jwt jwt,
                        @ModelAttribute CommentRequest request) {
                return commentService.createComment(jwt.getSubject(), request)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping
        public Mono<APIResponse<Comment>> getComment(@RequestParam String commentId) {
                return commentService.getComment(commentId)
                                .map(comment -> APIResponse
                                                .<Comment>builder()
                                                .result(comment)
                                                .build());
        }

        @GetMapping("/all")
        public Mono<APIResponse<Set<Comment>>> getAllComment() {
                return commentService.getAllComments()
                                .collect(Collectors.toSet())
                                .map(comments -> APIResponse
                                                .<Set<Comment>>builder()
                                                .result(comments)
                                                .build());
        }

        @GetMapping("/allFromAsset")
        public Mono<APIResponse<List<UserComment>>> getAllCommentsFrom1Asset(String assetId) {
                return commentService.getAllCommentsFrom1Asset(assetId)
                                .collect(Collectors.toList())
                                .map(comments -> APIResponse
                                                .<List<UserComment>>builder()
                                                .result(comments)
                                                .build());
        }

        @PutMapping
        public Mono<APIResponse<Comment>> putMethodName(@RequestParam String commentId,
                        @ModelAttribute CommentRequest request) {
                return commentService.updateComment(commentId, request)
                                .map(comment -> APIResponse
                                                .<Comment>builder()
                                                .result(comment)
                                                .build());
        }

        @DeleteMapping
        public Mono<APIResponse<?>> deleteMethodName(@RequestParam String commentId) {
                return commentService.deleteComment(commentId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }
}
