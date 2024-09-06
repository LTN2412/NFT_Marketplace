package com.nftmarketplace.user_service.model.dto.response;

import java.util.Date;
import java.util.Set;

import com.nftmarketplace.user_service.model.enums.Gender;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFlat {
    String id;

    String name;

    Gender gender;

    String email;

    String address;

    String phoneNumber;

    String avatarPath;

    String coverImgPath;

    Date createdAt;

    Date updatedAt;

    Boolean isAuthor;

    Set<String> friendIds;

    Set<String> followerIds;

    Set<String> assetIds;
}
