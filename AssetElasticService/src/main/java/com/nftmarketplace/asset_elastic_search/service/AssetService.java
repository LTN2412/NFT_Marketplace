package com.nftmarketplace.asset_elastic_search.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_search.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.Asset;
import com.nftmarketplace.asset_elastic_search.model.Author;
import com.nftmarketplace.asset_elastic_search.repository.AssetRepository;
import com.nftmarketplace.asset_elastic_search.repository.AuthorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetService {

    AssetRepository assetRepository;
    AuthorRepository authorRepository;

    public void consumerAsset(KafkaMessage<Asset> message) {
        Asset asset = message.getData();
        switch (message.getAction()) {
            case "CREATE":
                if (assetRepository.existsById(asset.getId())) {
                    // throw to topic errs
                }
                assetRepository.save(asset);
                Author author = authorRepository.findById(asset.getAuthorId()).orElseThrow();
                Set<String> assetIds = author.getAssetIds();
                assetIds.add(asset.getId());
                authorRepository.save(author);
                break;
            case "UPDATE":
                if (!assetRepository.existsById(message.getData().getId())) {
                    // throw to topic errs
                }
                assetRepository.save(message.getData());
                break;
            case "DELETE":
                if (!assetRepository.existsById(message.getData().getId())) {
                    // throw to topic errs
                }
                assetRepository.deleteById(message.getData().getId());
                break;
            default:
                break;
        }
    }

    @Cacheable(value = "asset", key = "#id")
    public Asset getAsset(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return asset;
    }

    public Set<Asset> getAllAssets() {
        Iterable<Asset> iterable = assetRepository.findAll();
        Set<Asset> allAssets = new HashSet<>();
        iterable.iterator().forEachRemaining(asset -> allAssets.add(asset));
        return allAssets;
    }

    public Set<Asset> findByNameFuzzy(String keyword) {
        Set<Asset> fuzzySearch = assetRepository.findByNameFuzzy(keyword);
        return fuzzySearch;
    }

    public Set<Asset> suggest(String keyword) {
        Set<Asset> suggest = assetRepository.suggest(keyword);
        return suggest;
    }
}
