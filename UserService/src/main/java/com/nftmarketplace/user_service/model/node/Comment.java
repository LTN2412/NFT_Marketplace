package com.nftmarketplace.user_service.model.node;

import java.util.Date;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Node("comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    String id;

    @Property
    String assetId;

    @Property
    String userComment;

    @Property
    Integer rating;

    @Property("created_at")
    Date createdAt = new Date();

    @Property("updated_at")
    Date updatedAt = new Date();
}