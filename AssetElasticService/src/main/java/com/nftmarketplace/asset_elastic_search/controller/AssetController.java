package com.nftmarketplace.asset_elastic_search.controller;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_elastic_search.model.Asset;
import com.nftmarketplace.asset_elastic_search.model.dto.APIResponse;
import com.nftmarketplace.asset_elastic_search.model.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_elastic_search.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/elastic/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {
    AssetService assetService;

    @Cacheable(value = "asset", key = "#id")
    @GetMapping("")
    public APIResponse<Asset> getAsset(@RequestParam String id) {
        Asset asset = assetService.getAsset(id);
        return APIResponse.<Asset>builder()
                .result(asset)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<Set<Asset>> getAllAsset() {
        Set<Asset> allAssets = assetService.getAllAssets();
        return APIResponse.<Set<Asset>>builder()
                .result(allAssets)
                .build();
    }

    @Cacheable(value = "assets", key = "#offset + '|' + #limit")
    @GetMapping("/page")
    public APIResponse<List<Asset>> getAssetsPageable(@RequestParam int offset, int limit) {
        Page<Asset> assetsPageable = assetService.getAssetsPageable(offset, limit);
        List<Asset> assets = assetsPageable.getContent();
        long totalElements = assetsPageable.getTotalElements();
        int totalPages = assetsPageable.getTotalPages();
        return APIResponse.<List<Asset>>builder()
                .totalElement(totalElements)
                .totalPage(totalPages)
                .result(assets)
                .build();
    }

    @GetMapping("/fromAuthor")
    public APIResponse<Set<Asset>> getAllAssetsFrom1Author(@RequestParam String id, int limit) {
        Set<Asset> allAssetsFrom1Author = assetService.getAllAssetsFrom1Author(id, limit);
        return APIResponse.<Set<Asset>>builder()
                .result(allAssetsFrom1Author)
                .build();
    }

    @GetMapping("/byTag")
    public APIResponse<Set<Asset>> getAllAssetsByTag(@RequestParam String name, int limit) {
        Set<Asset> allAssetsByTag = assetService.getAllAssetsByTag(name, limit);
        return APIResponse.<Set<Asset>>builder()
                .result(allAssetsByTag)
                .build();
    }

    @GetMapping("/search")
    public APIResponse<Set<Asset>> SearchAssets(@RequestParam String field, String query, int limit) {
        Set<Asset> searchAssets = assetService.searchAssets(field, query, limit);
        return APIResponse.<Set<Asset>>builder()
                .result(searchAssets)
                .build();
    }

    @KafkaListener(topics = "asset", groupId = "group-assets")
    public Void consumerAsset(KafkaMessage<AssetRequest> message) {
        assetService.consumerAsset(message);
        return null;
    }
}
