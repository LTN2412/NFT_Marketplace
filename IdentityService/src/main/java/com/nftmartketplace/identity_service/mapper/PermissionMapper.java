package com.nftmartketplace.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmartketplace.identity_service.dto.request.PermissionRequest;
import com.nftmartketplace.identity_service.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    Permission toPermission(PermissionRequest request);
}
