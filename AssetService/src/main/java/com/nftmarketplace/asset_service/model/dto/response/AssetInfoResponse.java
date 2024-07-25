package com.nftmarketplace.asset_service.model.dto.response;

import java.util.Set;

import com.nftmarketplace.asset_service.model.Tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetInfoResponse {
    String id;
    String name;
    String description;
    String imgPath;
    Float price;
    Float highestBid;
    String authorId;
    String authorName;
    String authorAvatarPath;
    Set<Tag> tags;
}
