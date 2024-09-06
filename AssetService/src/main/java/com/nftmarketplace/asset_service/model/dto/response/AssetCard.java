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
public class AssetCard {
    String id;

    String name;

    String imgPath;

    Float price;

    Float highestBid;

    String authorId; // * Author

    String authorName; // * Author

    String authorAvatarPath; // * Author
}