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
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {
        AssetService assetService;

        @GetMapping
        public Mono<APIResponse<Set<Asset>>> getAllAssetsInCartFrom1User(@RequestParam String userId) {
                return assetService.getAllAssetsInCartFrom1User(userId)
                                .collect(Collectors.toSet())
                                .map(assets -> APIResponse.<Set<Asset>>builder()
                                                .result(assets)
                                                .build());
        }

        @GetMapping("/select")
        public Mono<APIResponse<Set<Asset>>> getAllAssetsIsSelectInCartFrom1User(@RequestParam String userId) {
                return assetService.getAllAssetsIsSelectInCartFrom1User(userId)
                                .collect(Collectors.toSet())
                                .map(assets -> APIResponse.<Set<Asset>>builder()
                                                .result(assets)
                                                .build());
        }

        @PostMapping
        public Mono<APIResponse<?>> addAsset(@RequestBody AssetRequest request) {
                return assetService.addAsset(request)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/select")
        public Mono<APIResponse<?>> selectAssetInCart(@RequestParam String userId, String assetId, Boolean isSelect) {
                return assetService.selectAssetInCart(userId, assetId, isSelect)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @DeleteMapping
        public Mono<APIResponse<?>> removeAsset(@RequestParam String userId, String assetId) {
                return assetService.removeAsset(userId, assetId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        };

        @PostMapping("/order")
        public String postMethodName(@RequestBody String entity) {
                return entity;
        }
}
