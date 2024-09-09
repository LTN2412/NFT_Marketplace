package com.nftmarketplace.user_service.service;

import java.util.Set;

import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.dto.response.AssetInCart;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {
    Mono<String> addAsset(String userId, AssetRequest request);

    Mono<String> removeAsset(String userId, String assetId);

    Flux<AssetInCart> getAllAssetsInCartFrom1User(String userId);

    Mono<String> selectAssetInCart(String userId, String assetId, Boolean isSelect);

    Mono<Set<AssetInCart>> getAllAssetsIsSelectInCartFrom1User(String userId);

    Mono<String> deleteAllAssetsIsSelected(String userId);

    Mono<Void> checkAssetId(String assetId);
}