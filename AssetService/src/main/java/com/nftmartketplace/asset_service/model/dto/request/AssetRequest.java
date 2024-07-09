package com.nftmartketplace.asset_service.model.dto.request;

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
public class AssetRequest {

    String name;
    String description;
    Set<String> tags;
    Float price;
    Float highestBid;
    String imgPath;
    String authorId;

}