package com.nftmarketplace.asset_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    @EntityGraph(attributePaths = "assets")
    List<Author> findAll();

    @EntityGraph(attributePaths = "assets")
    Optional<Author> findById(String id);

    boolean existsByName(String id);
}