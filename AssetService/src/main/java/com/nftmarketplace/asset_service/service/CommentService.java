package com.nftmarketplace.asset_service.service;

import java.util.List;

import com.nftmarketplace.asset_service.model.Comment;
import com.nftmarketplace.asset_service.model.dto.request.CommentRequest;

public interface CommentService {
    Comment createComment(String userId, CommentRequest request);

    Comment getComment(String commentId);

    List<Comment> getAllCommentsFrom1Asset(String assetId);

    List<Comment> getAllComments();

    Comment updateComment(String commentId, CommentRequest request);

    Void deleteComment(String commentId);
}
