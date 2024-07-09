package com.nftmarketplace.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.identity_service.dto.request.RoleRequest;
import com.nftmarketplace.identity_service.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
}
