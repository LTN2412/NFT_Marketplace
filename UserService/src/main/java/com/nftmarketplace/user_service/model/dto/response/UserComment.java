package com.nftmarketplace.user_service.model.dto.response;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserComment {
    String id;

    String userId;

    String assetId;

    String username;

    String userAvatarPath;

    String userComment;

    Integer rating;

    Date createdAt;

    Date updatedAt;
}
