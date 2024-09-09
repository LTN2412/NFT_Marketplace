package com.nftmarketplace.user_service.model.kafka_model;

import com.nftmarketplace.user_service.model.enums.Update;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFollowerKafka {
    String userId;

    Update update;
}
