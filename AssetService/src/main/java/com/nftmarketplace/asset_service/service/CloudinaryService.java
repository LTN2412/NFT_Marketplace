package com.nftmarketplace.asset_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImg(MultipartFile file, String id, String prefix);
}