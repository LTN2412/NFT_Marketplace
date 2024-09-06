package com.nftmarketplace.asset_service.service;

import java.util.List;

import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;
import com.nftmarketplace.asset_service.model.kafka_model.AuthorKafka;

public interface AuthorService {
    Author createAuthor(AuthorRequest request);

    Author createAuthorFromKafka(AuthorKafka authorKafka);

    AuthorFlat getAuthorFlat(String id);

    List<Author> getAllAuthors();

    Author updateAuthor(String id, AuthorRequest request);

    Void deleteAuthor(String id);
}