package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetsPageable;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfo;
import com.nftmarketplace.asset_service.service.AssetService;
import com.nftmarketplace.asset_service.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/asset")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {
    AssetService assetService;

    @PostMapping("")
    public APIResponse<Asset> createAsset(@RequestBody AssetRequest request) {
        Asset asset = assetService.createAsset(request);
        return APIResponse.<Asset>builder()
                .result(asset)
                .build();
    }

    @Cacheable(value = "asset", key = "#id")
    @GetMapping("")
    public APIResponse<AssetInfo> getAsset(@RequestParam String id) {
        AssetInfo getAsset = assetService.getAssetId(id);
        return APIResponse.<AssetInfo>builder()
                .result(getAsset)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<Set<AssetFlat>> getAllAssets() {
        Set<Asset> allAssets = assetService.getAllAssets();
        Set<AssetFlat> assetsFlat = AssetMapper.INSTANCE.toAssetsFlat(allAssets);
        return APIResponse.<Set<AssetFlat>>builder()
                .result(assetsFlat)
                .build();
    }

    @Cacheable(value = "assets", key = "#offset+'|'+#limit")
    @GetMapping("/page")
    public APIResponse<List<AssetsPageable>> getAssetsPageable(@RequestParam int offset, @RequestParam int limit) {
        Page<AssetsPageable> assetsPageable = assetService.getAssetsPageable(offset, limit);
        return APIResponse.<List<AssetsPageable>>builder()
                .totalElement(assetsPageable.getTotalElements())
                .totalPage(assetsPageable.getTotalPages())
                .result(assetsPageable.getContent())
                .build();
    }

    @PutMapping("")
    public APIResponse<Asset> updateAsset(@RequestParam String id, @RequestBody AssetRequest request) {
        Asset updateAsset = assetService.updateAsset(id, request);
        return APIResponse.<Asset>builder()
                .result(updateAsset)
                .build();
    }

    @Caching(evict = {
            @CacheEvict(value = "assets", allEntries = true),
            @CacheEvict(value = "asset", key = "#id")
    })
    @DeleteMapping("")
    public APIResponse<Void> deleteAsset(@RequestParam String id) {
        assetService.deleteAsset(id);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }
}
