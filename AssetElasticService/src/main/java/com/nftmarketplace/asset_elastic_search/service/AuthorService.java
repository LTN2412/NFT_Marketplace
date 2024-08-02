package com.nftmarketplace.asset_elastic_search.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_search.model.Author;
import com.nftmarketplace.asset_elastic_search.model.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_elastic_search.repository.AssetRepository;
import com.nftmarketplace.asset_elastic_search.repository.AuthorRepository;
import com.nftmarketplace.asset_elastic_search.utils.mapper.AuthorMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {

    AuthorRepository authorRepository;
    AssetRepository assetRepository;
    CacheManager cacheManager;

    public void consumerAuthor(KafkaMessage<AuthorRequest> message) {
        AuthorRequest request = message.getData();
        String key = request.getId();
        switch (message.getAction()) {
            case "CREATE":
                if (authorRepository.existsById(request.getId())) {
                    // throw to topic errs
                }
                Author author = AuthorMapper.INSTANCE.toAuthor(request);
                if (request.getAssetIds() == null)
                    author.setAssetIds(new HashSet<>());
                authorRepository.save(author);
                break;
            case "UPDATE":
                if (!authorRepository.existsById(request.getId())) {
                    // throw to topic errs
                }
                authorRepository.save(AuthorMapper.INSTANCE.toAuthor(request));
                cacheManager.getCache("author").evict(key);
                break;
            case "DELETE":

                Author deleteAuthor = authorRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException());
                Set<String> assetIds = deleteAuthor.getAssetIds();
                assetRepository.deleteAllById(assetIds);
                authorRepository.deleteById(request.getId());
                cacheManager.getCache("author").evict(key);
                break;
            default:
                break;
        }
    }

    public Author getAuthor(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return author;
    }

    public Set<Author> getAllAuthors() {
        Set<Author> allAuthors = new HashSet<>();
        authorRepository.findAll()
                .iterator().forEachRemaining(asset -> allAuthors.add(asset));
        return allAuthors;
    }
}
