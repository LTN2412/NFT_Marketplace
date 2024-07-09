package com.nftmarketplace.user_service.model.dto.request;

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
public class UserRequest {
    String firstName;
    String lastName;
    String gender;
    String email;
    String address;
    String phoneNumber;
    String avatarPath;
    String username;
}
