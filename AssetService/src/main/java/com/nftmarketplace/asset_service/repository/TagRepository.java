package com.nftmarketplace.asset_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
}