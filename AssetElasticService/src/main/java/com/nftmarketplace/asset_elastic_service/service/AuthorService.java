package com.nftmarketplace.asset_elastic_service.service;

import com.nftmarketplace.asset_elastic_service.model.Author;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> getAuthor(String authorId);

    Flux<Author> getAllAuthors();

    Flux<Author> getTopAuthors(Integer limit);

    Mono<Long> countAllAuthors();

    Mono<Void> checkExistAuthors(String... authorIds);
}
