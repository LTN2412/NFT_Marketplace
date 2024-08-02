package com.nftmarketplace.asset_service.utils.faker;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.nftmarketplace.asset_service.configuration.exception.AppException;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.service.AssetService;
import com.nftmarketplace.asset_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class FakerService {
    Faker faker = new Faker();
    AuthorService authorService;
    AssetService assetService;

    public Void generateFakeData(int count) {
        try {
            for (int i = 0; i < count; i++) {
                AuthorRequest author = AuthorRequest.builder()
                        .name(faker.name().fullName() + String.valueOf(faker.random().nextInt(1, 100)))
                        // .avatarPath(faker.internet().image())
                        // .coverImgPath(faker.internet().image())
                        .volumne((float) faker.number().randomDouble(2, 1000, 10000))
                        .nftSolds((int) faker.number().randomDigit())
                        .followers((int) faker.number().randomDigit())
                        .bio(faker.lorem().paragraph())
                        .build();
                Author saveAuthor = authorService.createAuthor(author);
                String author_id = saveAuthor.getId();
                for (int j = 0; j < count * 1; j++) {
                    HashSet<String> tags = new HashSet<>();
                    tags.add(faker.commerce().material());
                    tags.add(faker.commerce().department());
                    AssetRequest asset = AssetRequest.builder()
                            .name(faker.commerce().productName() + String.valueOf(faker.random().nextInt(1, 100)))
                            .description(faker.lorem().paragraph())
                            .tags(tags)
                            .price((float) faker.number().randomDouble(2, 10, 1000))
                            .highestBid((float) faker.number().randomDouble(2, 10, 1000))
                            // .imgPath(faker.internet().image())
                            .authorId(author_id)
                            .build();
                    assetService.createAsset(asset);
                }
            }
        } catch (AppException e) {
        }
        return null;
    }
}
