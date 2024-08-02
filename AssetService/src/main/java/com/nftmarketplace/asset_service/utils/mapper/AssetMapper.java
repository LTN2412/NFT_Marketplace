package com.nftmarketplace.asset_service.utils.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.Tag;
import com.nftmarketplace.asset_service.model.dto.request.AssetElastic;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfo;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "timestampCreate", ignore = true)
    Asset toAsset(AssetRequest request);

    AssetFlat toAssetFlat(Asset asset);

    Set<AssetFlat> toAssetsFlat(Set<Asset> assets);

    AssetElastic toAssetElastic(Asset asset);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampCreate", ignore = true)
    Asset toAsset(AssetRequest request, @MappingTarget Asset asset);

    @Mapping(source = "asset.id", target = "id")
    @Mapping(source = "asset.name", target = "name")
    @Mapping(target = "asset.tags")
    @Mapping(target = "author.id", ignore = true)
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "author.avatarPath", target = "authorAvatarPath")
    AssetInfo toAssetInfo(Asset asset, Author author);

    default Set<String> getTags(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
}
