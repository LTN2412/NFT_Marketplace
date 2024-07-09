package com.nftmartketplace.identity_service.dto.projection;

import java.util.List;

public interface UserProjection {
    String getUsername();

    String getPassword();

    List<RoleProjection> getRoles();

    interface RoleProjection {
        String getName();
    }

}