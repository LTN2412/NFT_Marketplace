package com.nftmarketplace.asset_elastic_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_service.exception.AppException;
import com.nftmarketplace.asset_elastic_service.exception.ErrorCode;
import com.nftmarketplace.asset_elastic_service.model.Author;
import com.nftmarketplace.asset_elastic_service.repository.AuthorRepository;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public Mono<Author> getAuthor(String authorId) {
        return checkExistAuthors(authorId).then(Mono.defer(() -> authorRepository.findById(authorId)));
    }

    @Override
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Long> countAllAuthors() {
        return authorRepository.count();
    }

    @Override
    public Flux<Author> getTopAuthors(Integer limit) {
        return authorRepository
                .findTopAuthorByVolumne(PageRequest.of(0, limit, Sort.by(new Order(Direction.DESC, "volumne"))));
    }

    @Override
    public Mono<Void> checkExistAuthors(String... authorIds) {
        return Flux.fromArray(authorIds)
                .flatMap(authorId -> authorRepository.existsById(authorId)
                        .flatMap(exist -> exist ? Mono.empty()
                                : Mono.error(
                                        new AppException(ErrorCode.NOT_EXISTED, "Author " + authorId + " not exist"))))
                .then();
    }
}
