package com.nftmarketplace.asset_service.service.impl;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.asset_service.exception.AppException;
import com.nftmarketplace.asset_service.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.Comment;
import com.nftmarketplace.asset_service.model.dto.request.CommentRequest;
import com.nftmarketplace.asset_service.model.enums.Action;
import com.nftmarketplace.asset_service.model.kafka_model.CommentKafka;
import com.nftmarketplace.asset_service.repository.CommentRepository;
import com.nftmarketplace.asset_service.service.CommentService;
import com.nftmarketplace.asset_service.utils.mapper.CommentMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Override
    public Comment createComment(String userId, CommentRequest request) {
        Comment comment = CommentMapper.INSTANCE.toComment(request);
        comment.setUserId(userId);
        Comment saveComment = commentRepository.save(comment);
        // * Send Kafka
        CommentKafka commentKafka = CommentMapper.INSTANCE.toCommentKafka(saveComment);
        commentKafka.setAction(Action.CREATE);
        kafkaTemplate.send("comment", gson.toJson(commentKafka));
        return saveComment;
    }

    @Override
    public Comment getComment(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
    }

    @Override
    public List<Comment> getAllCommentsFrom1Asset(String assetId) {
        return commentRepository.findAllByAssetId(assetId);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment updateComment(String commentId, CommentRequest request) {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Comment newComment = CommentMapper.INSTANCE.toComment(request, oldComment);
        // * Send Kafka
        CommentKafka commentKafka = CommentMapper.INSTANCE.toCommentKafka(newComment);
        commentKafka.setAction(Action.UPDATE);
        kafkaTemplate.send("comment", gson.toJson(commentKafka));
        return commentRepository.save(newComment);
    }

    @Override
    public Void deleteComment(String commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        commentRepository.deleteById(commentId);
        // * Send Kafka
        CommentKafka commentKafka = new CommentKafka();
        commentKafka.setId(commentId);
        commentKafka.setAction(Action.DELETE);
        kafkaTemplate.send("comment", gson.toJson(commentKafka));
        return null;
    }
}
