package com.nftmarketplace.user_service.model.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetRequest {
    String id;

    String name;

    Float price;

    Integer quantity;

    String imgPath;
}
