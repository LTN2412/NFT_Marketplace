package com.nftmarketplace.asset_elastic_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_elastic_service.model.Asset;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AssetKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    Asset toAsset(AssetKafka assetKafka);
}
