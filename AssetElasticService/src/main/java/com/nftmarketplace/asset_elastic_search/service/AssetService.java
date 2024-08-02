package com.nftmarketplace.asset_elastic_search.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_search.model.Asset;
import com.nftmarketplace.asset_elastic_search.model.Author;
import com.nftmarketplace.asset_elastic_search.model.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_elastic_search.repository.AssetRepository;
import com.nftmarketplace.asset_elastic_search.repository.AuthorRepository;
import com.nftmarketplace.asset_elastic_search.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AssetService {

    AssetRepository assetRepository;
    AuthorRepository authorRepository;
    CacheManager cacheManager;

    public void consumerAsset(KafkaMessage<AssetRequest> message) {
        AssetRequest request = message.getData();
        String key = request.getId();
        switch (message.getAction()) {
            case "CREATE":
                Author author = authorRepository.findById(request.getAuthorId()).orElseThrow();
                Set<String> assets = author.getAssetIds();
                assets.add(request.getId());
                authorRepository.save(author);
                Asset asset = AssetMapper.INSTANCE.toAsset(request);
                asset.setAuthorName(author.getName());
                asset.setAuthorAvatarPath(author.getAvatarPath());
                assetRepository.save(asset);
                break;
            case "UPDATE":
                if (!assetRepository.existsById(request.getId())) {
                    // throw to topic errs
                }
                Asset updateAsset = AssetMapper.INSTANCE.toAsset(request);
                assetRepository.save(updateAsset);
                cacheManager.getCache("asset").evict(key);
                break;
            case "DELETE":
                if (!assetRepository.existsById(request.getId())) {
                    // throw to topic errs
                }
                Author deleteAssetAuthor = authorRepository.findById(request.getAuthorId()).orElseThrow();
                assetRepository.deleteById(request.getId());
                deleteAssetAuthor.getAssetIds().remove(request.getId());
                authorRepository.save(deleteAssetAuthor);
                cacheManager.getCache("asset").evict(key);
                break;
            default:
                break;
        }
    }

    public Asset getAsset(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return asset;
    }

    public Set<Asset> getAllAssets() {
        Iterable<Asset> iterable = assetRepository.findAll();
        Set<Asset> assets = new HashSet<>();
        for (Asset item : iterable) {
            assets.add(item);
        }
        return assets;
    }

    public Page<Asset> getAssetsPageable(int offset, int limit) {
        Page<Asset> assets = assetRepository.findAll(PageRequest.of(offset, limit));
        return assets;
    }

    public Set<Asset> getAllAssetsFrom1Author(String id, int limit) {
        Set<Asset> allAssetsFrom1Author = assetRepository.findAllAssetsFrom1Author(id, limit);
        return allAssetsFrom1Author;
    }

    public Set<Asset> getAllAssetsByTag(String name, int limit) {
        Set<Asset> allAssetsByTag = assetRepository.findAllAssetsByTag(name, limit);
        return allAssetsByTag;
    }

    public Set<Asset> searchAssets(String field, String query, int limit) {
        // Query Fuzzy and Query Wildcard
        String queryWildCard = query;
        if (query.length() > 1)
            queryWildCard = query.substring(0, query.length() - 1);
        Set<Asset> searchAssets = assetRepository.searchAssets(field, query,
                queryWildCard, limit);
        return searchAssets;
    }

    // List<String> authorIds = new ArrayList<>();
    // assets.forEach(asset -> {
    // authorIds.add(asset.getAuthorId());
    // });

    // List<String> authorName = new ArrayList<>();
    // List<String> authorAvatarPath = new ArrayList<>();

    // authorRepository.findAllById(authorIds).iterator().forEachRemaining(author ->
    // {
    // authorName.add(author.getName());
    // authorAvatarPath.add(author.getAvatarPath());
    // });

    // log.info("AUTHORNAME:{}", authorName.size());
    // log.info("AUTHORAVATAR:{}", authorAvatarPath.size());
    // log.info("ASSET:{}", assets.size());

    // Set<AssetCardResponse> assetCardResponse = new HashSet<>();
    // for (int i = 0; i < assets.size(); i++) {
    // assetCardResponse.add(AssetMapper.INSTANCE.toAssetCardResponse(assets.get(i),
    // authorName.get(i),
    // authorAvatarPath.get(i)));
    // }
    // return assetCardResponse;
    // }

    // public Set<Asset> getAllAssets() {
    // Iterable<Asset> iterable = assetRepository.findAll();
    // Set<Asset> allAssets = new HashSet<>();
    // iterable.iterator().forEachRemaining(asset -> allAssets.add(asset));
    // return allAssets;
    // }

    // public Set<Asset> findByNameFuzzy(String keyword) {
    // Set<Asset> fuzzySearch = assetRepository.findByNameFuzzy(keyword);
    // return fuzzySearch;
    // }

    // public Set<Asset> suggest(String keyword) {
    // Set<Asset> suggest = assetRepository.suggest(keyword);
    // return suggest;
    // }
}
