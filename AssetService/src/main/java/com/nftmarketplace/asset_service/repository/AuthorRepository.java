package com.nftmarketplace.asset_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    boolean existsByName(String name);

    @EntityGraph(attributePaths = { "assets", "assets.tags" })
    List<Author> findAll();

    @EntityGraph(attributePaths = { "assets", "assets.tags" })
    Optional<Author> findById(String id);
}