package com.nftmarketplace.asset_elastic_search.model.dto.request;

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
public class AuthorRequest {
    String id;
    String name;
    String avatarPath;
    String coverImgPath;
    String bio;
    Float volumne;
    Long nftSolds;
    Long followers;
    Set<String> assetIds;
}
