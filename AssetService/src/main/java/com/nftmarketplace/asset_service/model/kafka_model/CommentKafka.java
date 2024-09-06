package com.nftmarketplace.asset_service.model.kafka_model;

import java.util.Date;

import com.nftmarketplace.asset_service.model.enums.Action;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentKafka {
    String id;

    String userId;

    String userNickname;

    String userAvatarPath;

    String userComment;

    Integer rating;

    String assetId;

    Date createdAt;

    Date updatedAt;

    Action action;
}
