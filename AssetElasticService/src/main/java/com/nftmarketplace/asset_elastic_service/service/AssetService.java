package com.nftmarketplace.asset_elastic_service.service;

import com.nftmarketplace.asset_elastic_service.model.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {
    Mono<Asset> getAsset(String assetId);

    Flux<Asset> getAllAssets();

    Flux<Asset> getAssetsPageable(Integer offset, Integer limit);

    Flux<Asset> getAllAssetsFrom1Author(String authoId, Integer limit);

    Flux<Asset> getAllAssetsByTag(String tagName, Integer limit);

    Flux<Asset> searchAssets(String field, String query, Integer limit);

    Mono<Long> countAllAssets();

    Mono<Void> checkExistAssets(String... assetIds);
}
