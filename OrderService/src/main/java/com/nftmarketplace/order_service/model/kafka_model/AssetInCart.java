package com.nftmarketplace.order_service.model.kafka_model;

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
public class AssetInCart {
    String id;

    String name;

    Float price;

    Integer quantity;

    String imgPath;

    Date createdAt;

    Date updatedAt;

    Boolean isSelect;
}
