package com.nftmarketplace.user_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.user_service.model.dto.request.AssetRequest;
import com.nftmarketplace.user_service.model.node.Asset;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    Asset toAsset(AssetRequest request);
}
