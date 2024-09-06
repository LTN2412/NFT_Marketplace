package com.nftmarketplace.asset_elastic_service.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.nftmarketplace.asset_elastic_service.service.CommentService;
import com.nftmarketplace.asset_elastic_service.model.Comment;
import com.nftmarketplace.asset_elastic_service.model.dto.APIResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/elastic/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
        CommentService commentService;

        @GetMapping
        public Mono<APIResponse<Comment>> getComment(@RequestParam String commentId) {
                return commentService.getComment(commentId)
                                .map(comment -> APIResponse
                                                .<Comment>builder()
                                                .result(comment)
                                                .build());
        }

        @GetMapping("/allFromAsset")
        public Mono<APIResponse<Set<Comment>>> getAllByAssetIdOrderByUpdatedAt(@RequestParam String assetId) {
                return commentService.getAllByAssetIdOrderByUpdatedAt(assetId)
                                .collect(Collectors.toSet())
                                .map(comments -> APIResponse
                                                .<Set<Comment>>builder()
                                                .result(comments)
                                                .build());
        }

        @GetMapping("/all")
        public Mono<APIResponse<Set<Comment>>> getAllComments() {
                return commentService.getAllComments()
                                .collect(Collectors.toSet())
                                .map(comments -> APIResponse
                                                .<Set<Comment>>builder()
                                                .result(comments)
                                                .build());
        }
}
