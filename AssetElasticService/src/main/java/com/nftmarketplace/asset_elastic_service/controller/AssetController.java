package com.nftmarketplace.asset_elastic_service.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_elastic_service.model.Asset;
import com.nftmarketplace.asset_elastic_service.model.dto.APIResponse;
import com.nftmarketplace.asset_elastic_service.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/elastic/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
        AssetService assetService;

        @GetMapping
        public Mono<APIResponse<Asset>> getAsset(@RequestParam String assetId) {
                return assetService.getAsset(assetId)
                                .map(asset -> APIResponse
                                                .<Asset>builder()
                                                .result(asset)
                                                .build());
        }

        @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<APIResponse<Asset>> getAllAssets() {
                return assetService.getAllAssets()
                                .map(asset -> APIResponse
                                                .<Asset>builder()
                                                .result(asset)
                                                .build());
        }

        @GetMapping(path = "/page")
        public Mono<APIResponse<Set<Asset>>> getAllAssetsPageable(@RequestParam Integer offset, Integer limit) {
                return Mono.zip(assetService.getAssetsPageable(offset, limit)
                                .collect(Collectors.toSet()), assetService.countAllAssets()).flatMap(tuple -> {
                                        return Mono.just(APIResponse
                                                        .<Set<Asset>>builder()
                                                        .result(tuple.getT1())
                                                        .totalElement(tuple.getT2())
                                                        .totalPage(Math.ceilDiv(tuple.getT2(), limit))
                                                        .build());
                                });
        }

        @GetMapping(path = "/fromAuthor")
        public Mono<APIResponse<Set<Asset>>> getAllAssetsFrom1Author(@RequestParam String authorId, Integer limit) {
                return assetService.getAllAssetsFrom1Author(authorId, limit)
                                .collect(Collectors.toSet())
                                .map(asset -> APIResponse
                                                .<Set<Asset>>builder()
                                                .result(asset)
                                                .build());
        }

        @GetMapping(path = "/byTag")
        public Mono<APIResponse<Set<Asset>>> getAllAssetsByTag(@RequestParam String tagName, Integer limit) {
                return assetService.getAllAssetsByTag(tagName, limit)
                                .collect(Collectors.toSet())
                                .map(asset -> APIResponse
                                                .<Set<Asset>>builder()
                                                .result(asset)
                                                .build());
        }

        @GetMapping(path = "/search")
        public Mono<APIResponse<Set<Asset>>> searchAssets(@RequestParam String field, String query, Integer limit) {
                return assetService.searchAssets(field, query, limit)
                                .collect(Collectors.toSet())
                                .map(asset -> APIResponse
                                                .<Set<Asset>>builder()
                                                .result(asset)
                                                .build());
        }

        @GetMapping("/count")
        public Mono<APIResponse<Long>> countAllAssets() {
                return assetService.countAllAssets()
                                .map(numberAssets -> APIResponse
                                                .<Long>builder()
                                                .result(numberAssets)
                                                .build());
        }
}
