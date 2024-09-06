package com.nftmarketplace.asset_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.dto.response.AssetCard;

public interface AssetService {
    Asset createAsset(AssetRequest request);

    AssetFlat getAssetFlat(String assetId);

    List<Asset> getAllAssets();

    Page<AssetCard> getAssetCardsPageable(Integer offset, Integer limit);

    Asset updateAsset(String assetId, AssetRequest request);

    Void deleteAsset(String assetId);
}