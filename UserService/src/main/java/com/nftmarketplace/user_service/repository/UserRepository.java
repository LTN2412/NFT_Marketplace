package com.nftmarketplace.user_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.user_service.model.User;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    Boolean existsByUsername(String username);
}
