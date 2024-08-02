package com.nftmarketplace.asset_service.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetsPageable {
    String id;
    String name;
    String imgPath;
    Float price;
    Float highestBid;
    String authorId;
    String authorName;
    String authorAvatarPath;
}
// public interface AssetCard {
// String getId();

// String getName();

// String getImgPath();

// String getPrice();

// String getHighestBid();

// String getAuthorId();

// String getAuthorName();

// String getAuthorAvatarPath();
// }
