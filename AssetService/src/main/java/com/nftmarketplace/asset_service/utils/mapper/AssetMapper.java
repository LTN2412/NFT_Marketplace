package com.nftmarketplace.asset_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AssetElastic;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfoResponse;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampCreate", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Asset toAsset(AssetRequest request);

    @Mapping(target = "tags", ignore = true)
    AssetElastic toAssetElastic(Asset asset);

    @Mapping(target = "author.id", ignore = true)
    @Mapping(source = "asset.id", target = "id")
    @Mapping(source = "asset.id", target = "name")
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "author.avatarPath", target = "authorAvatarPath")
    AssetInfoResponse toAssetInfoResponse(Asset asset, Author author);

}
