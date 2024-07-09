package com.nftmartketplace.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmartketplace.identity_service.dto.request.UserCreationRequest;
import com.nftmartketplace.identity_service.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
}
