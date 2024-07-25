// package com.nftmarketplace.asset_service.service;

// import org.springframework.stereotype.Service;

// import com.nftmarketplace.asset_service.configuration.exception.AppException;
// import com.nftmarketplace.asset_service.configuration.exception.ErrorCode;
// import com.nftmarketplace.asset_service.model.TagAsset;
// import com.nftmarketplace.asset_service.repository.TagAssetRepository;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @Service
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE)
// public class TagAssetService {
// TagAssetRepository tagRepository;

// public TagAsset createTagAsset(TagAsset tag) {
// if (tagRepository.existsById(tag.getName()))
// throw new AppException(ErrorCode.EXISTED);
// TagAsset tagAsset = tagRepository.save(tag);
// return tagAsset;
// }
// }
