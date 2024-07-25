package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetCardResponse;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfoResponse;
import com.nftmarketplace.asset_service.service.AssetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("")
    public APIResponse<AssetInfoResponse> getAsset(@RequestParam String id) {
        AssetInfoResponse getAsset = assetService.getAssetId(id);
        return APIResponse.<AssetInfoResponse>builder()
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

    @GetMapping("/card")
    public APIResponse<List<AssetCardResponse>> getAssetCards(@RequestParam int page, @RequestParam int limit) {
        Page<AssetCardResponse> assetCards = assetService.getAssetCards(page, limit);
        return APIResponse.<List<AssetCardResponse>>builder()
                .totalElement(assetCards.getTotalElements())
                .totalPage(assetCards.getTotalPages())
                .result(assetCards.getContent())
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
