package com.nftmartketplace.asset_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nftmartketplace.asset_service.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    Boolean existsByName(String name);
}
