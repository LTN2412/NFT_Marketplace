package com.nftmarketplace.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.identity_service.dto.request.PermissionRequest;
import com.nftmarketplace.identity_service.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    Permission toPermission(PermissionRequest request);
}
