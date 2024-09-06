package com.nftmarketplace.asset_service.utils.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Tag;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.kafka_model.AssetKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    @Mapping(target = "tags", ignore = true)
    Asset toAsset(AssetRequest request);

    @Mapping(target = "action", ignore = true)
    AssetKafka toAssetKafka(Asset asset);

    AssetFlat toAssetFlat(Asset asset);

    List<AssetFlat> toAssetFlatList(List<Asset> assets);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tags", ignore = true)
    Asset toAsset(AssetRequest request, @MappingTarget Asset asset);

    default Set<String> getTags(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Asset asset) {
        asset.setUpdatedAt(new Date());
    }
}
