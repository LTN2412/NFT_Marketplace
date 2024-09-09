package com.nftmarketplace.user_service.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.producer.EventProducer;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.dto.response.AssetInCart;
import com.nftmarketplace.user_service.model.kafka_model.AssetsOrderKafka;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.repository.CartRepository;
import com.nftmarketplace.user_service.service.CartService;
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
public class CartServiceImpl implements CartService {
        CartRepository assetRepository;
        UserService userService;
        EventProducer eventProducer;
        Gson gson = new Gson();

        @Override
        public Mono<String> addAsset(String userId, AssetRequest request) {
                return userService.checkExistUsers(userId)
                                .then(Mono.defer(() -> {
                                        Asset asset = AssetMapper.INSTANCE.toAsset(request);
                                        return assetRepository.save(asset)
                                                        .then(assetRepository.addAsset(request.getId(), userId))
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
        public Flux<AssetInCart> getAllAssetsInCartFrom1User(String userId) {
                return userService.checkExistUsers(userId)
                                .thenMany(Flux.defer(() -> assetRepository.findAllAssetsInCartFrom1User(userId)));
        }

        @Override
        public Mono<String> selectAssetInCart(String userId, String assetId, Boolean isSelect) {
                return Mono.when(userService.checkExistUsers(userId), checkAssetId(assetId))
                                .then(Mono.defer(() -> assetRepository.selectAssetInCart(userId, assetId, isSelect)
                                                .then(Mono.just("Change select asset completed!"))));
        }

        @Override
        public Mono<Set<AssetInCart>> getAllAssetsIsSelectInCartFrom1User(String userId) {
                return userService.checkExistUsers(userId)
                                .thenMany(assetRepository.findAllAssetsIsSelectInCartFrom1User(userId))
                                .collect(Collectors.toSet())
                                .flatMap(assets -> {
                                        AssetsOrderKafka assetsOrderKafka = AssetsOrderKafka.builder()
                                                        .assetIds(assets.stream().map(AssetInCart::getId)
                                                                        .collect(Collectors.toSet()))
                                                        .userId(userId)
                                                        .build();
                                        eventProducer.send("order", gson.toJson(assetsOrderKafka)).subscribe();
                                        return Mono.just(assets);
                                });
        }

        @Override
        public Mono<String> deleteAllAssetsIsSelected(String userId) {
                return userService.checkExistUsers(userId)
                                .then(assetRepository.removeAllSelectedAssetsFromCart(userId))
                                .then(Mono.just("Order deleted successfully"));
        }

        @Override
        public Mono<Void> checkAssetId(String assetId) {
                return assetRepository.existsById(assetId)
                                .flatMap(exist -> exist ? Mono.empty()
                                                : Mono.error(new AppException(ErrorCode.NOT_EXISTED,
                                                                "Asset " + assetId + " not exist")));
        }
}
