package com.nftmarketplace.asset_elastic_service.service;

import java.util.Set;

import com.nftmarketplace.asset_elastic_service.model.Asset;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AssetKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.CommentKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {
    Mono<Void> consumeAsset(AssetKafka assetKafka);

    Mono<Void> addComment(CommentKafka commentKafka);

    Mono<Asset> getAsset(String assetId);

    Flux<Asset> getAllAssetsById(Set<String> request);

    Flux<Asset> getAllAssets();

    Flux<Asset> getAssetsPageable(Integer offset, Integer limit);

    Flux<Asset> getAllAssetsFrom1Author(String authoId, Integer limit);

    Flux<Asset> getAllAssetsByTag(String tagName, Integer limit);

    Flux<Asset> searchAssets(String field, String query, Integer limit);

    Mono<Long> countAllAssets();

    Mono<Void> checkExistAssets(String... assetIds);
}
