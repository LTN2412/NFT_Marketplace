package com.nftmarketplace.user_service.model.dto.request;

import org.springframework.data.neo4j.core.schema.Property;

import com.nftmarketplace.user_service.model.enums.Gender;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name;

    @NotNull
    Gender gender;

    @NotNull
    String address;

    @Pattern(regexp = "^\\+84[0-9]{9,10}$|^0[0-9]{9,10}$", message = "The phone number is not in the correct format")
    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 characters")
    @Property("phone_number")
    String phoneNumber;
    // FilePart avatarImg;

    // FilePart coverImg;
}
