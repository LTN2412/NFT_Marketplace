package com.nftmarketplace.asset_elastic_service.model.kafka_model;

import java.util.Date;
import java.util.Set;

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
public class AssetKafka {
    String id;

    String name;

    String description;

    Float price;

    Float highestBid;

    String imgPath;

    Set<String> tags;

    String authorId;

    String authorName;

    String authorAvatarPath;

    Date createdAt;

    Date updatedAt;

    Action action;
}
