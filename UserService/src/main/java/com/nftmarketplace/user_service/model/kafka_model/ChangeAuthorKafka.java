package com.nftmarketplace.user_service.model.kafka_model;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeAuthorKafka {
    String id;

    String name;

    String avatarPath;

    String coverImgPath;

    Date createdAt;

    Date updatedAt;
}
