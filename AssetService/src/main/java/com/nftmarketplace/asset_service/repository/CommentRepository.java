package com.nftmarketplace.asset_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByAssetId(String assetId);
}