package com.nftmarketplace.identity_service.model.dto.request;

import java.io.Serializable;

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
public class IdentityRequest implements Serializable {
    @NotNull
    String username;

    @NotNull
    String password;
}
