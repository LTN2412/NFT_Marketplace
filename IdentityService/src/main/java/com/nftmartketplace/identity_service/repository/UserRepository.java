package com.nftmartketplace.identity_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nftmartketplace.identity_service.dto.projection.UserProjection;
import com.nftmartketplace.identity_service.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("select u from User u")
    List<UserProjection> findAllUser();

    @EntityGraph(attributePaths = { "roles", "roles.permissions" })
    @Query("select u from User u")
    List<UserProjection> findAllRoles();
}