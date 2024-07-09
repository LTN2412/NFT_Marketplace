package com.nftmartketplace.asset_elastic_search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.nftmartketplace.asset_elastic_search.model.Author;

public interface AuthorRepository extends ElasticsearchRepository<Author, String> {
}
