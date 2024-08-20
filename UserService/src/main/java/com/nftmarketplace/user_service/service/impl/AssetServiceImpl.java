package com.nftmarketplace.user_service.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

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

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetServiceImpl implements AssetService {
    AssetRepository assetRepository;
    UserService userService;

    @Override
    public Mono<String> addAsset(AssetRequest request) {
        return userService.checkExistUsers(request.getUserId()).then(Mono.defer(() -> {
            Asset asset = AssetMapper.INSTANCE.toAsset(request);
            return assetRepository.save(asset).then(assetRepository.addAsset(request.getId(), request.getUserId()))
                    .then(Mono.just("Added asset successfully!"));
        }));
    }

    @Override
    public Mono<String> removeAsset(String userId, String assetId) {
        return assetRepository.checkAsset(userId, assetId)
                .flatMap(exist -> exist
                        ? assetRepository.removeAsset(userId, assetId).then(Mono.just("Removed asset successfully!"))
                        : Mono.error(new AppException(ErrorCode.NOT_EXISTED)));
    }

    @Override
    public Mono<Set<Asset>> getAssetsFrom1User(String userId) {
        return userService.checkExistUsers(userId).then(Mono.defer(() -> {
            return assetRepository.findAllAssetsFrom1User(userId).collect(Collectors.toSet());
        }));
    }
}
