package com.nftmartketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmartketplace.asset_service.model.Asset;
import com.nftmartketplace.asset_service.model.dto.APIResponse;
import com.nftmartketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmartketplace.asset_service.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/asset")
public class AssetController {
    AssetService assetService;

    @PostMapping("")
    public APIResponse<Asset> createAsset(@RequestBody AssetRequest request) {
        Asset asset = assetService.createAsset(request);
        return APIResponse.<Asset>builder()
                .result(asset)
                .build();
    }

    @GetMapping("")
    public APIResponse<Asset> getAsset(@RequestParam String id) {
        Asset getAsset = assetService.getAsset(id);
        return APIResponse.<Asset>builder()
                .result(getAsset)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<Asset>> getAllAssets() {
        List<Asset> allAssets = assetService.getAllAssets();
        return APIResponse.<List<Asset>>builder()
                .result(allAssets)
                .build();
    }

    @PutMapping("")
    public APIResponse<Asset> updateAsset(@RequestParam String id, @RequestBody AssetRequest request) {
        Asset updateAsset = assetService.updateAsset(id, request);
        return APIResponse.<Asset>builder()
                .result(updateAsset)
                .build();
    }

    @DeleteMapping("")
    public APIResponse<Void> deleteAsset(@RequestParam String id) {
        assetService.deleteAsset(id);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }
}
