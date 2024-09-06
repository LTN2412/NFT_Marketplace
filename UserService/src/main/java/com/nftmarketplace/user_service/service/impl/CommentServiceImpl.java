package com.nftmarketplace.user_service.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.CommentRequest;
import com.nftmarketplace.user_service.model.dto.response.UserComment;
import com.nftmarketplace.user_service.model.node.Comment;
import com.nftmarketplace.user_service.repository.CommentRepository;
import com.nftmarketplace.user_service.service.CommentService;
import com.nftmarketplace.user_service.service.UserService;
import com.nftmarketplace.user_service.utils.mapper.CommentMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    UserService userService;

    @Override
    public Mono<String> createComment(String userId, CommentRequest request) {
        return userService.checkExistUsers(userId)
                .then(Mono.defer(() -> {
                    Comment comment = CommentMapper.INSTANCE.toComment(request);
                    comment.setId(UUID.randomUUID().toString());
                    return commentRepository.save(comment)
                            .then(commentRepository.addComment(comment.getId(), userId))
                            .then(Mono.just("Added comment successfully!"));
                }));
    }

    @Override
    public Mono<Comment> getComment(String commentId) {
        return checkCommentId(commentId).then(Mono.defer(() -> commentRepository.findById(commentId)));
    }

    @Override
    public Flux<UserComment> getAllCommentsFrom1Asset(String assetId) {
        return commentRepository.findAllCommentFrom1AssetId(assetId);
    }

    @Override
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<Comment> updateComment(String commentId, CommentRequest request) {
        return checkCommentId(commentId).then(Mono.defer(() -> commentRepository.findById(commentId).flatMap(
                oldComment -> {
                    Comment newComment = CommentMapper.INSTANCE.toComment(request, oldComment);
                    return commentRepository.save(newComment);
                })));
    }

    @Override
    public Mono<String> deleteComment(String commentId) {
        return checkCommentId(commentId).then(Mono.defer(() -> commentRepository.deleteById(commentId)
                .then(Mono.just("Delete " + commentId + " successfully"))));
    }

    @Override
    public Mono<Void> checkCommentId(String commentId) {
        return commentRepository.existsById(commentId)
                .flatMap(exist -> exist ? Mono.empty()
                        : Mono.error(new AppException(ErrorCode.NOT_EXISTED, "Comment " + commentId + " not exist")));
    }
}
