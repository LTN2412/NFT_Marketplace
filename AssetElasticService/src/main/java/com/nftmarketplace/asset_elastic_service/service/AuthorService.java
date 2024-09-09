package com.nftmarketplace.asset_elastic_service.service;

import com.nftmarketplace.asset_elastic_service.model.Author;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AssetKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.ChangeAuthorKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.UpdateFollowerKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Void> consumeAuthor(AuthorKafka authorKafka);

    Mono<Void> changeAuthor(ChangeAuthorKafka changeAuthorKafka);

    Mono<Void> updateFollower(UpdateFollowerKafka updateFollowerKafka);

    Mono<Void> addAsset(AssetKafka assetKafka);

    Mono<Author> getAuthor(String authorId);

    Flux<Author> getAuthorsPageale(Integer offset, Integer limit);

    Flux<Author> getAllAuthors();

    Flux<Author> getTopAuthors(Integer limit);

    Mono<Long> countAllAuthors();

    Flux<Author> searchAuthors(String query, Integer limit);

    Mono<Void> checkExistAuthors(String... authorIds);
}
