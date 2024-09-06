package com.nftmarketplace.asset_elastic_service.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
@Document(indexName = "asset")
public class Asset {
    @Id
    @Field(type = FieldType.Keyword)
    String id;

    @Field(type = FieldType.Text)
    String name;

    @Field(type = FieldType.Text)
    String description;

    @Field(type = FieldType.Text)
    Set<String> tags;

    @Field(type = FieldType.Float)
    Float price;

    @Field(type = FieldType.Float, name = "highest_bid")
    Float highestBid;

    @Field(type = FieldType.Keyword, name = "img_path", index = false)
    String imgPath;

    @Field(type = FieldType.Keyword, name = "author_id")
    String authorId;

    // *Author property
    @Field(type = FieldType.Text, name = "author_name")
    String authorName;

    // *Author property
    @Field(type = FieldType.Keyword, name = "author_avatar_path", index = false)
    String authorAvatarPath;

    @Field(type = FieldType.Keyword)
    Set<String> commentIds = new HashSet<>();

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, name = "created_at")
    Date createdAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, name = "updated_at")
    Date updatedAt;
}
