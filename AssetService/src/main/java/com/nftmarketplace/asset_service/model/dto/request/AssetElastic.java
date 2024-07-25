package com.nftmarketplace.asset_service.model.dto.request;

import java.util.Date;
import java.util.Set;

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
public class AssetElastic {
    String id;
    String name;
    String description;
    Date timestampCreate;
    Set<String> tags;
    Float price;
    Float highestBid;
    String imgPath;
    String authorId;
}
