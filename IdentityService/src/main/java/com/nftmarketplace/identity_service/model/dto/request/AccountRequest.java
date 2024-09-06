package com.nftmarketplace.identity_service.model.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
    @NotNull
    String username;

    @NotNull
    String password;

    @NotNull
    @Email
    String email;

    Boolean isAuthor = false;

    Set<String> roles;
}
