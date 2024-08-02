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
public class AuthorFlat {
    String id;
    String name;
    String avatarPath;
    String coverImgPath;
    String bio;
    Float volumne;
    Long nftSolds;
    Long followers;
    Set<AssetFlat> assets;
}
