package com.nftmarketplace.asset_elastic_service.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "author")
public class Author {
    @Id
    @Field(type = FieldType.Keyword)
    String id;

    @Field(type = FieldType.Text)
    String name;

    @Field(type = FieldType.Keyword, name = "avatar_path", index = false)
    String avatarPath;

    @Field(type = FieldType.Keyword, name = "cover_img_path", index = false)
    String coverImgPath;

    @Field(type = FieldType.Text)
    String bio;

    @Field(type = FieldType.Float)
    Float volumne;

    @Field(type = FieldType.Integer, name = "nft_solds", fielddata = true)
    Integer nftSolds;

    @Field(type = FieldType.Integer)
    Integer followers;

    @Field(type = FieldType.Keyword)
    Set<String> assetIds;
}
