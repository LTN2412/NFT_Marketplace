package com.nftmarketplace.asset_elastic_search.controller;

import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_elastic_search.dto.APIResponse;
import com.nftmarketplace.asset_elastic_search.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.Asset;
import com.nftmarketplace.asset_elastic_search.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/elastic/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
    AssetService assetService;

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

    @GetMapping("/fuzzy")
    public APIResponse<Set<Asset>> getByNameFuzzy(@RequestParam String k) {
        Set<Asset> byNameFuzzy = assetService.findByNameFuzzy(k);
        return APIResponse.<Set<Asset>>builder()
                .result(byNameFuzzy)
                .build();
    }

    @GetMapping("/suggest")
    public APIResponse<Set<Asset>> suggestion(@RequestParam String k) {
        Set<Asset> suggest = assetService.suggest(k);
        return APIResponse.<Set<Asset>>builder()
                .result(suggest)
                .build();
    }

    @KafkaListener(topics = "asset", groupId = "group-assets")
    public Void createAsset(KafkaMessage<Asset> message) {
        assetService.consumerAsset(message);
        return null;
    }
}
