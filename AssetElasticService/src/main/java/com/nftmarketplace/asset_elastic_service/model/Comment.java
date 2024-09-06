package com.nftmarketplace.asset_elastic_service.model;

import java.util.Date;

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
@Document(indexName = "comment")
public class Comment {
    @Id
    @Field(type = FieldType.Keyword)
    String id;

    @Field(type = FieldType.Text, name = "user_nickname")
    String userNickname;

    @Field(type = FieldType.Keyword, name = "user_avatar_path")
    String userAvatarPath;

    @Field(type = FieldType.Float)
    Integer rating;

    @Field(type = FieldType.Text, name = "user_comment")
    String userComment;

    @Field(type = FieldType.Keyword, name = "asset_id")
    String assetId;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, name = "created_at")
    Date createdAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, name = "updated_at")
    Date updatedAt;
}
