package com.nftmarketplace.asset_service.model.dto.request;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

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

    Float price;

    Float highestBid;

    MultipartFile imgAsset;

    String authorId;

    Set<String> tags;
}