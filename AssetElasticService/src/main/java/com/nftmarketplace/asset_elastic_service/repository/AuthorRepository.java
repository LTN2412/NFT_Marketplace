package com.nftmarketplace.asset_elastic_service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_elastic_service.model.Author;

import reactor.core.publisher.Flux;

@Repository
@EnableElasticsearchRepositories
public interface AuthorRepository extends ReactiveElasticsearchRepository<Author, String> {

    public static final ReactiveElasticsearchOperations reactiveElasticsearchOperations = null;

    @Query("""
            {
                "match_all": {}
            }
            """)
    Flux<Author> findTopAuthorByVolumne(Pageable pageable);
}