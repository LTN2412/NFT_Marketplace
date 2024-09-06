package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetCard;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.service.AssetService;
import com.nftmarketplace.asset_service.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/asset")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {
    AssetService assetService;

    @PostMapping
    public APIResponse<Asset> createAsset(@ModelAttribute AssetRequest request) {
        Asset asset = assetService.createAsset(request);
        return APIResponse.<Asset>builder()
                .result(asset)
                .build();
    }

    @Cacheable(value = "asset", key = "#assetId")
    @GetMapping
    public APIResponse<AssetFlat> getAssetFlat(@RequestParam String assetId) {
        AssetFlat assetFlat = assetService.getAssetFlat(assetId);
        return APIResponse.<AssetFlat>builder()
                .result(assetFlat)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<AssetFlat>> getAllAssets() {
        List<Asset> allAssets = assetService.getAllAssets();
        List<AssetFlat> AllAssetsFlat = AssetMapper.INSTANCE.toAssetFlatList(allAssets);
        return APIResponse.<List<AssetFlat>>builder()
                .result(AllAssetsFlat)
                .build();
    }

    @Cacheable(value = "assets", key = "#offset+'|'+#limit")
    @GetMapping("/page")
    public APIResponse<List<AssetCard>> getAssetsPageable(@RequestParam Integer offset, @RequestParam Integer limit) {
        Page<AssetCard> assetsPageable = assetService.getAssetCardsPageable(offset, limit);
        return APIResponse.<List<AssetCard>>builder()
                .totalElement(assetsPageable.getTotalElements())
                .totalPage(assetsPageable.getTotalPages())
                .result(assetsPageable.getContent())
                .build();
    }

    @Caching(evict = {
            @CacheEvict(value = "assets", allEntries = true),
            @CacheEvict(value = "asset", key = "#assetId")
    })
    @PutMapping
    public APIResponse<Asset> updateAsset(@RequestParam String assetId, @ModelAttribute AssetRequest request) {
        Asset updateAsset = assetService.updateAsset(assetId, request);
        return APIResponse.<Asset>builder()
                .result(updateAsset)
                .build();
    }

    @Caching(evict = {
            @CacheEvict(value = "assets", allEntries = true),
            @CacheEvict(value = "asset", key = "#assetId")
    })
    @DeleteMapping
    public APIResponse<Void> deleteAsset(@RequestParam String assetId) {
        assetService.deleteAsset(assetId);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }
}
