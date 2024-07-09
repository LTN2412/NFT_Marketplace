package com.nftmartketplace.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmartketplace.identity_service.dto.request.RoleRequest;
import com.nftmartketplace.identity_service.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
}
