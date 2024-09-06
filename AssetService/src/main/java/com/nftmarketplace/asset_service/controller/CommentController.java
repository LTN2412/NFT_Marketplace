package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Comment;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.CommentRequest;
import com.nftmarketplace.asset_service.service.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/comment")
public class CommentController {
    CommentService commentService;

    @PostMapping
    public APIResponse<Comment> createComment(@AuthenticationPrincipal Jwt jwt,
            @ModelAttribute CommentRequest request) {
        Comment comment = commentService.createComment(jwt.getSubject(), request);
        return APIResponse.<Comment>builder()
                .result(comment)
                .build();
    }

    @GetMapping
    public APIResponse<Comment> getComment(@AuthenticationPrincipal Jwt jwt) {
        Comment comment = commentService.getComment(jwt.getSubject());
        return APIResponse.<Comment>builder()
                .result(comment)
                .build();
    }

    @GetMapping("/allFromAsset")
    public APIResponse<List<Comment>> getAllCommentsFrom1Asset(@RequestParam String assetId) {
        List<Comment> allComments = commentService.getAllCommentsFrom1Asset(assetId);
        return APIResponse.<List<Comment>>builder()
                .result(allComments)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<Comment>> getAllComment() {
        List<Comment> allComments = commentService.getAllComments();
        return APIResponse.<List<Comment>>builder()
                .result(allComments)
                .build();
    }

    @PutMapping
    public APIResponse<Comment> updateComment(@RequestParam String commentId, @ModelAttribute CommentRequest request) {
        Comment comment = commentService.updateComment(commentId, request);
        return APIResponse.<Comment>builder()
                .result(comment)
                .build();
    }

    @DeleteMapping
    public APIResponse<?> deleteComment(@RequestParam String commentId) {
        commentService.deleteComment(commentId);
        return APIResponse.builder()
                .message("Delete comment " + commentId + " successfully")
                .build();
    }
}
