package com.nftmarketplace.user_service.controller;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.service.CloudinaryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryController {
    CloudinaryService cloudinaryService;

    @PostMapping("/avatarPath")
    public Mono<String> createAvatarPath(@AuthenticationPrincipal Jwt jwt, @RequestPart Mono<FilePart> avatar) {
        return cloudinaryService.createAvatarPath(avatar, jwt.getSubject());
    }
}
