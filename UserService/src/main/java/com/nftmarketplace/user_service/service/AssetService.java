package com.nftmarketplace.user_service.service;

import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {
    Mono<String> addAsset(String userId, AssetRequest request);

    Mono<String> removeAsset(String userId, String assetId);

    Flux<Asset> getAllAssetsInCartFrom1User(String userId);

    Mono<String> selectAssetInCart(String userId, String assetId, Boolean isSelect);

    Flux<Asset> getAllAssetsIsSelectInCartFrom1User(String userId);

    Mono<Void> checkAssetId(String assetId);
}