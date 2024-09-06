package com.nftmarketplace.asset_elastic_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_service.exception.AppException;
import com.nftmarketplace.asset_elastic_service.exception.ErrorCode;
import com.nftmarketplace.asset_elastic_service.model.Author;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AssetKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.ChangeAuthorKafka;
import com.nftmarketplace.asset_elastic_service.repository.AuthorRepository;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;
import com.nftmarketplace.asset_elastic_service.utils.mapper.AuthorMapper;

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
    public Mono<Void> consumeAuthor(AuthorKafka authorKafka) {
        return switch (authorKafka.getAction()) {
            case CREATE, UPDATE -> authorRepository.save(AuthorMapper.INSTANCE.toAuthor(authorKafka)).then();
            case DELETE -> authorRepository.deleteById(authorKafka.getId());
            default -> Mono.empty();
        };
    }

    @Override
    public Mono<Void> changeAuthor(ChangeAuthorKafka changeAuthorKafka) {
        return authorRepository.save(AuthorMapper.INSTANCE.toAuthor(changeAuthorKafka)).then();
    }

    @Override
    public Mono<Void> addAsset(AssetKafka assetKafka) {
        return authorRepository.findById(assetKafka.getAuthorId())
                .flatMap(author -> {
                    author.getAssetIds().add(assetKafka.getId());
                    return authorRepository.save(author);
                })
                .then();
    }

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
                .findAll(PageRequest.of(0, limit, Sort.by(new Order(Direction.DESC, "volumne"))));
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
