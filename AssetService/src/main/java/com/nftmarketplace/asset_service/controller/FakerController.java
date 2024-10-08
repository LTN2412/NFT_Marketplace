package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.utils.faker.FakerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FakerController {
    FakerService fakerService;

    @GetMapping("/faker")
    public APIResponse<?> createFakeData(@RequestParam Integer numberAuthor, Integer numberAsset,
            Integer numberComment) {
        fakerService.generateFakeData(numberAuthor, numberAsset, numberComment);
        return APIResponse.builder()
                .message("Create fake data completed!")
                .build();
    }
}
