package com.nftmarketplace.order_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.order_service.model.Order;
import com.nftmarketplace.order_service.model.kafka_model.AssetsOrderKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(AssetsOrderKafka assetsOrderKafka);
}
