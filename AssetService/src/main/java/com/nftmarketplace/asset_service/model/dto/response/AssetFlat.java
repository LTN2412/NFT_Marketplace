package com.nftmarketplace.asset_service.model.dto.response;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetFlat {
    String id;
    String name;
    String description;
    String imgPath;
    Float price;
    Float highestBid;
    Set<String> tags;
}