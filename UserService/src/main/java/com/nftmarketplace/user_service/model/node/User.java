package com.nftmarketplace.user_service.model.node;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Node("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    @Property("first_name")
    String firstName;

    @Property("last_name")
    String lastName;

    @Property
    String gender;

    @Property
    String email;

    @Property
    String address;

    @Property("phone_number")
    String phoneNumber;

    @Property("avatar_path")
    String avatarPath;

    @Property("created_at")
    Date createdAt = new Date();

    @Property("updated_at")
    Date updatedAt = new Date();

    @Property("last_login")
    Date lastLogin = new Date();

    @Property
    String username;

    @Property
    Boolean isAuthor = false;

    @Relationship(type = "IS_FRIEND", direction = Relationship.Direction.OUTGOING)
    Set<User> friends = new HashSet<>();

    @Relationship(type = "IS_FOLLOWER", direction = Relationship.Direction.OUTGOING)
    Set<User> followers = new HashSet<>();

    @Relationship(type = "HAS_IN_CART", direction = Relationship.Direction.OUTGOING)
    Set<Asset> assets = new HashSet<>();
}