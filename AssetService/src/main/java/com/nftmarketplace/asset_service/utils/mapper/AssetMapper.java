package com.nftmarketplace.asset_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampCreate", ignore = true)
    Asset toAsset(AssetRequest request);
}
