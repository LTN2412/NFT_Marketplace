package com.nftmarketplace.user_service.service;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

public interface CloudinaryService {
    public Mono<byte[]> convertFilePartToBytes(Mono<FilePart> filePartMono);

    public Mono<String> createImgPath(Mono<FilePart> filePart, String userId);
}
