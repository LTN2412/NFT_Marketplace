package com.nftmarketplace.asset_elastic_search.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_elastic_search.model.Asset;
import com.nftmarketplace.asset_elastic_search.model.dto.request.AssetRequest;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    Asset toAsset(AssetRequest request);
}
