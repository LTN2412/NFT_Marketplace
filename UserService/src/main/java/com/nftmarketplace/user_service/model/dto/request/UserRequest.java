package com.nftmarketplace.user_service.model.dto.request;

import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.http.codec.multipart.FilePart;

import jakarta.validation.constraints.Email;
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
public class UserRequest {
    @Size(min = 36, max = 36, message = "Not valid UUID")
    String id;

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    String firstName;

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    String lastName;

    String gender;

    @Size(max = 50)
    @Email(message = "Input must be in Email format")
    String email;

    String address;

    @Pattern(regexp = "^\\+84[0-9]{9,10}$|^0[0-9]{9,10}$", message = "The phone number is not in the correct format")
    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 characters")
    @Property("phone_number")
    String phoneNumber;

    FilePart avatarImg;

    @Size(min = 7, max = 100, message = "Username must be between 7 and 100 characters")
    String username;
}
