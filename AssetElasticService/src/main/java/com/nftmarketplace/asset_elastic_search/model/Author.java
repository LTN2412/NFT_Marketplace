package com.nftmarketplace.asset_elastic_search.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "author")
public class Author {

    @Id
    String id;

    @Field(type = FieldType.Text)
    String name;

    @Field(type = FieldType.Text, name = "avatar_path")
    String avatarPath;

    @Field(type = FieldType.Text, name = "cover_img_path")
    String coverImgPath;

    @Field(type = FieldType.Float)
    Float volumne;

    @Field(type = FieldType.Integer, name = "nft_solds")
    Long nftSolds;

    @Field(type = FieldType.Integer)
    Long followers;

    @Field(type = FieldType.Keyword, name = "asset_ids")
    Set<String> assetIds;

}
