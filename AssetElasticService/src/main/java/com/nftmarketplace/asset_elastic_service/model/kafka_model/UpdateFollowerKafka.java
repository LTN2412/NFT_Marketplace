package com.nftmarketplace.asset_elastic_service.model.kafka_model;

import com.nftmarketplace.asset_elastic_service.model.enums.Update;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFollowerKafka {
    String userId;

    Update update;
}