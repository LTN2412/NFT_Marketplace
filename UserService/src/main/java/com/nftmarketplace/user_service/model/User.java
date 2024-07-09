package com.nftmarketplace.user_service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("user")
public class User {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
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
    Date createdAt;

    @Property("updated_at")
    Date updatedAt;

    @Property("last_login")
    Date lastLogin;

    @Property
    String username;

}