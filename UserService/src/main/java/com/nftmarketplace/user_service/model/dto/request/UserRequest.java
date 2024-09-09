package com.nftmarketplace.user_service.model.dto.request;

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
public class UserRequest {
    String id;

    String name;

    Gender gender;

    String email;

    String address;

    String phoneNumber;

    Boolean isAuthor = false;
}
