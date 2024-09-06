package com.nftmarketplace.asset_service.utils.faker;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.nftmarketplace.asset_service.exception.AppException;
import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.request.CommentRequest;
import com.nftmarketplace.asset_service.service.AssetService;
import com.nftmarketplace.asset_service.service.AuthorService;
import com.nftmarketplace.asset_service.service.CommentService;

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
    CommentService commentService;

    public Void generateFakeData(Integer numberAuthor, Integer numberAsset, Integer numberComment) {
        try {
            for (int i = 0; i < numberAuthor; i++) {
                AuthorRequest author = AuthorRequest.builder()
                        .name(faker.name().fullName() + String.valueOf(faker.random().nextInt(1, 100)))
                        .bio(faker.lorem().paragraph())
                        .volumne((float) faker.number().randomDouble(2, 1000, 10000))
                        .nftSolds(faker.number().randomDigit())
                        .followers(faker.number().randomDigit())
                        .build();
                Author saveAuthor = authorService.createAuthor(author);
                String authorId = saveAuthor.getId();
                Integer randomNumberAsset = faker.random().nextInt(1, numberAsset);
                for (int j = 0; j < randomNumberAsset; j++) {
                    HashSet<String> tags = new HashSet<>();
                    tags.add(faker.commerce().material());
                    tags.add(faker.commerce().department());
                    AssetRequest asset = AssetRequest.builder()
                            .name(faker.commerce().productName() + String.valueOf(faker.random().nextInt(1, 100)))
                            .description(faker.lorem().paragraph())
                            .tags(tags)
                            .price((float) faker.number().randomDouble(2, 10, 1000))
                            .highestBid((float) faker.number().randomDouble(2, 10, 1000))
                            .authorId(authorId)
                            .build();
                    Asset saveAsset = assetService.createAsset(asset);
                    String assetId = saveAsset.getId();
                    Integer randomNumberComment = faker.random().nextInt(1, numberComment);
                    for (int k = 0; k < randomNumberComment; k++) {
                        CommentRequest comment = CommentRequest.builder()
                                .userNickname(faker.name().username())
                                .userComment(faker.lordOfTheRings().location())
                                .rating(faker.random().nextInt(1, 5))
                                .assetId(assetId)
                                .build();
                        commentService.createComment(UUID.randomUUID().toString(), comment);
                    }
                }
            }
        } catch (AppException e) {
        }
        return null;
    }
}
