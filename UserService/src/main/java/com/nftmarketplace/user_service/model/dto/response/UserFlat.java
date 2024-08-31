package com.nftmarketplace.user_service.model.dto.response;

import java.util.Date;
import java.util.Set;

import com.nftmarketplace.user_service.model.enums.Gender;

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
public class UserFlat {
    String id;

    String firstName;

    String lastName;

    Gender gender;

    String email;

    String address;

    String phoneNumber;

    String avatarPath;

    Date createdAt;

    Date updatedAt;

    Date lastLogin;

    String username;

    Set<String> friendIds;

    Set<String> followerIds;

    Set<String> assetIds;
}
