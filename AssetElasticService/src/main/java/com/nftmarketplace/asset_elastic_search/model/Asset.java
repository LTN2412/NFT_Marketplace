package com.nftmarketplace.asset_elastic_search.model;

import java.util.Date;
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
    String id;

    @Field(type = FieldType.Keyword)
    String name;

    @Field(type = FieldType.Text)
    String description;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    Date timestamp_create;

    @Field(type = FieldType.Keyword)
    Set<String> tags;

    @Field(type = FieldType.Float)
    Float price;

    @Field(type = FieldType.Float, name = "highest_bid")
    Float highestBid;

    @Field(type = FieldType.Text, name = "img_path")
    String imgPath;

    @Field(type = FieldType.Keyword, name = "author_id")
    String authorId;
}
