package com.nftmarketplace.user_service.service.impl;

import org.springframework.stereotype.Service;

import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.repository.AssetRepository;
import com.nftmarketplace.user_service.service.AssetService;
import com.nftmarketplace.user_service.service.UserService;
import com.nftmarketplace.user_service.utils.mapper.AssetMapper;

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
    UserService userService;

    @Override
    public Mono<String> addAsset(AssetRequest request) {
        return userService.checkExistUsers(request.getUserId())
                .then(Mono.defer(() -> {
                    Asset asset = AssetMapper.INSTANCE.toAsset(request);
                    return assetRepository.save(asset)
                            .then(assetRepository.addAsset(request.getId(), request.getUserId()))
                            .then(Mono.just("Added asset successfully!"));
                }));
    }

    @Override
    public Mono<String> removeAsset(String userId, String assetId) {
        return Mono.when(userService.checkExistUsers(userId), checkAssetId(assetId))
                .then(Mono.defer(() -> assetRepository.removeAsset(userId, assetId)
                        .then(Mono.just("Removed asset successfully!"))));
    }

    @Override
    public Flux<Asset> getAllAssetsInCartFrom1User(String userId) {
        return userService.checkExistUsers(userId).thenMany(Flux.defer(() -> {
            return assetRepository.findAllAssetsInCartFrom1User(userId);
        }));
    }

    @Override
    public Mono<String> selectAssetInCart(String userId, String assetId, Boolean isSelect) {
        return Mono.when(userService.checkExistUsers(userId), checkAssetId(assetId))
                .then(Mono.defer(() -> assetRepository.selectAssetInCart(userId, assetId, isSelect)
                        .then(Mono.just("Change select asset completed!"))));
    }

    @Override
    public Flux<Asset> getAllAssetsIsSelectInCartFrom1User(String userId) {
        return userService.checkExistUsers(userId).thenMany(Flux.defer(() -> {
            return assetRepository.findAllAssetsIsSelectInCartFrom1User(userId);
        }));
    }

    @Override
    public Mono<Void> checkAssetId(String assetId) {
        return assetRepository.existsById(assetId)
                .flatMap(exist -> exist ? Mono.empty()
                        : Mono.error(new AppException(ErrorCode.NOT_EXISTED, "Asset " + assetId + " not exist")));
    }
}
