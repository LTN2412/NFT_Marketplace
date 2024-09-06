package com.nftmarketplace.asset_elastic_service.repository;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

import com.nftmarketplace.asset_elastic_service.model.Comment;

import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveElasticsearchRepository<Comment, String> {
    Flux<Comment> findAllByAssetIdOrderByUpdatedAt(String assetId);
}
