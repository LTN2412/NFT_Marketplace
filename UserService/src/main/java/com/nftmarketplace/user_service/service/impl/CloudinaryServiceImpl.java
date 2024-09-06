package com.nftmarketplace.user_service.service.impl;

import java.util.Map;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.service.CloudinaryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryServiceImpl implements CloudinaryService {
    Cloudinary cloudinary;

    @SuppressWarnings("deprecation")
    @Override
    public Mono<byte[]> convertFilePartToBytes(Mono<FilePart> filePart) {
        return filePart.flatMap(t -> DataBufferUtils.join(t.content())
                .map(dataBuffer -> dataBuffer.asByteBuffer().array()));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Mono<String> createImgPath(Mono<FilePart> filePart, String userId) {
        return convertFilePartToBytes(filePart).flatMap(img -> {
            return Mono.fromCallable(() -> {
                Map result = cloudinary.uploader().upload(img, ObjectUtils.asMap(
                        "public_id", "avatar_" + userId));
                return (String) result.get("secure_url");
            });
        }).onErrorResume(e -> Mono.error(new AppException(ErrorCode.UNKNOW_ERROR)));
    }
}
