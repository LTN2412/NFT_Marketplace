package com.nftmarketplace.asset_elastic_service.model.kafka_model;

import java.util.Date;

import com.nftmarketplace.asset_elastic_service.model.enums.Action;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorKafka {
    String id;

    String name;

    String avatarPath;

    String coverImgPath;

    Float volumne;

    Long nftSolds;

    Long followers;

    Date createdAt;

    Date updatedAt;

    Action action;
}