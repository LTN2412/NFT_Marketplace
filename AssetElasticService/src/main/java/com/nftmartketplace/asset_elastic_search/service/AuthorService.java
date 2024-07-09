package com.nftmartketplace.asset_elastic_search.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nftmartketplace.asset_elastic_search.dto.kakfa.KafkaMessage;
import com.nftmartketplace.asset_elastic_search.model.Author;
import com.nftmartketplace.asset_elastic_search.repository.AuthorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {

    AuthorRepository authorRepository;

    public void consumerAuthor(KafkaMessage<Author> message) {
        switch (message.getAction()) {
            case "CREATE":
                if (authorRepository.existsById(message.getData().getId())) {
                    // throw to topic errs
                }
                authorRepository.save(message.getData());
                break;
            case "UPDATE":
                if (!authorRepository.existsById(message.getData().getId())) {
                    // throw to topic errs
                }
                authorRepository.save(message.getData());
                break;
            case "DELETE":
                if (!authorRepository.existsById(message.getData().getId())) {
                    // throw to topic errs
                }
                authorRepository.deleteById(message.getData().getId());
                break;
            default:
                break;
        }
    }

    @Cacheable(value = "author", key = "#id")
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
