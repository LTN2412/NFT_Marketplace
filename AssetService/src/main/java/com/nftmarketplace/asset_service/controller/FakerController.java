package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.utils.faker.FakerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FakerController {
    FakerService fakerService;

    @GetMapping("/faker")
    public APIResponse<?> createFakeData(@RequestParam int count) {
        fakerService.generateFakeData(count);
        return APIResponse.builder()
                .message("Create fake data completed!")
                .build();
    }

}
