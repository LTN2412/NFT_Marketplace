package com.nftmarketplace.identity_service.utils;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.RoleRequest;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleRequest request);
}
