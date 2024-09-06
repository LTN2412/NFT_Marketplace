package com.nftmarketplace.asset_elastic_service.service;

import com.nftmarketplace.asset_elastic_service.model.Comment;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.CommentKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {
    Mono<Void> consumeComment(CommentKafka commentKafka);

    Mono<Comment> getComment(String commentId);

    Flux<Comment> getAllByAssetIdOrderByUpdatedAt(String assetId);

    Flux<Comment> getAllComments();

    Mono<Void> checkExistComments(String... commentIds);
}
