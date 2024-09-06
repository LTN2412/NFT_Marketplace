package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.service.CartService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
        CartService assetService;

        @GetMapping
        public Mono<APIResponse<Set<Asset>>> getAllAssetsInCartFrom1User(@AuthenticationPrincipal Jwt jwt) {
                return assetService.getAllAssetsInCartFrom1User(jwt.getSubject())
                                .collect(Collectors.toSet())
                                .map(assets -> APIResponse.<Set<Asset>>builder()
                                                .result(assets)
                                                .build());
        }

        @GetMapping("/select")
        public Mono<APIResponse<Set<Asset>>> getAllAssetsIsSelectInCartFrom1User(@AuthenticationPrincipal Jwt jwt) {
                return assetService.getAllAssetsIsSelectInCartFrom1User(jwt.getSubject())
                                .collect(Collectors.toSet())
                                .map(assets -> APIResponse.<Set<Asset>>builder()
                                                .result(assets)
                                                .build());
        }

        @PostMapping
        public Mono<APIResponse<?>> addAsset(@AuthenticationPrincipal Jwt jwt, @RequestBody AssetRequest request) {
                return assetService.addAsset(jwt.getSubject(), request)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/select")
        public Mono<APIResponse<?>> selectAssetInCart(@AuthenticationPrincipal Jwt jwt, @RequestParam String assetId,
                        Boolean isSelect) {
                return assetService.selectAssetInCart(jwt.getSubject(), assetId, isSelect)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @DeleteMapping
        public Mono<APIResponse<?>> removeAsset(@AuthenticationPrincipal Jwt jwt, @RequestParam String assetId) {
                return assetService.removeAsset(jwt.getSubject(), assetId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        };
}
