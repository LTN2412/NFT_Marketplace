package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
    AssetService assetService;

    @PostMapping
    public Mono<APIResponse<?>> addAsset(@RequestBody AssetRequest request) {
        return assetService.addAsset(request).flatMap(message -> {
            return Mono.just(APIResponse
                    .builder()
                    .message(message)
                    .build());
        });
    }

    @GetMapping()
    public Mono<APIResponse<Set<Asset>>> getMethodName(@RequestParam String userId) {
        return assetService.getAssetsFrom1User(userId).flatMap(assets -> {
            return Mono.just(APIResponse
                    .<Set<Asset>>builder()
                    .result(assets)
                    .build());
        });
    }

    @DeleteMapping
    public Mono<APIResponse<?>> removeAsset(@RequestParam String userId, @RequestParam String assetId) {
        return assetService.removeAsset(userId, assetId).flatMap(message -> {
            return Mono.just(APIResponse
                    .builder()
                    .message(message)
                    .build());
        });
    }
}
