package com.nftmarketplace.asset_service.model.dto.request;

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
public class AuthorRequest {
    String name;

    String bio;

    Float volumne;

    Integer nftSolds;

    Integer followers;

    MultipartFile avatar;

    MultipartFile coverImg;
}
