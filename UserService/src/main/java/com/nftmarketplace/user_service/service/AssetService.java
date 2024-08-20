package com.nftmarketplace.user_service.service;

import java.util.Set;

import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;

import reactor.core.publisher.Mono;

public interface AssetService {
    Mono<String> addAsset(AssetRequest request);

    Mono<String> removeAsset(String userId, String assetId);

    Mono<Set<Asset>> getAssetsFrom1User(String userId);
}