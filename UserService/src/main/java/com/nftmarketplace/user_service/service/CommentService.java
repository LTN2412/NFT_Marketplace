package com.nftmarketplace.user_service.service;

import com.nftmarketplace.user_service.model.dto.request.CommentRequest;
import com.nftmarketplace.user_service.model.dto.response.UserComment;
import com.nftmarketplace.user_service.model.node.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {
    Mono<String> createComment(String userId, CommentRequest request);

    Mono<Comment> getComment(String commentId);

    Flux<UserComment> getAllCommentsFrom1Asset(String assetId);

    Flux<Comment> getAllComments();

    Mono<Comment> updateComment(String commentId, CommentRequest request);

    Mono<String> deleteComment(String commentId);

    Mono<Void> checkCommentId(String commentId);
}
