package com.nftmarketplace.asset_elastic_service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_service.exception.AppException;
import com.nftmarketplace.asset_elastic_service.exception.ErrorCode;
import com.nftmarketplace.asset_elastic_service.model.Asset;
import com.nftmarketplace.asset_elastic_service.repository.AssetRepository;
import com.nftmarketplace.asset_elastic_service.service.AssetService;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetServiceImpl implements AssetService {

    AssetRepository assetRepository;
    AuthorService authorService;

    @Override
    public Mono<Asset> getAsset(String assetId) {
        return checkExistAssets(assetId).then(Mono.defer(() -> assetRepository.findById(assetId)));
    }

    @Override
    public Flux<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    @Override
    public Flux<Asset> getAssetsPageable(Integer offset, Integer limit) {
        return assetRepository.findAssetsPageable(PageRequest.of(offset, limit));
    }

    @Override
    public Flux<Asset> getAllAssetsFrom1Author(String authorId, Integer limit) {
        return authorService.checkExistAuthors(authorId)
                .thenMany(Flux
                        .defer(() -> assetRepository.findAllAssetsFrom1Author(authorId, PageRequest.ofSize(limit))));
    }

    @Override
    public Flux<Asset> getAllAssetsByTag(String tagName, Integer limit) {
        return assetRepository.findAllAssetsByTag(tagName, PageRequest.ofSize(limit));
    }

    @Override
    public Flux<Asset> searchAssets(String field, String query, Integer limit) {
        String queryWildCard = query;
        if (query.length() > 1)
            queryWildCard = query.substring(0, query.length() - 1);
        return assetRepository.searchAssets(field, query, queryWildCard, limit);
    }

    @Override
    public Mono<Long> countAllAssets() {
        return assetRepository.count();
    }

    @Override
    public Mono<Void> checkExistAssets(String... assetIds) {
        return Flux.fromArray(assetIds)
                .flatMap(assetId -> assetRepository.existsById(assetId)
                        .flatMap(exist -> exist ? Mono.empty()
                                : Mono.error(new AppException(ErrorCode.EXISTED, "AssetId " + assetId + " not exist"))))
                .then();
    }
}
