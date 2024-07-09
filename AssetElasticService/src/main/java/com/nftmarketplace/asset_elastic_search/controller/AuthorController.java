package com.nftmarketplace.asset_elastic_search.controller;

import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_elastic_search.dto.APIResponse;
import com.nftmarketplace.asset_elastic_search.dto.kakfa.KafkaMessage;
import com.nftmarketplace.asset_elastic_search.model.Author;
import com.nftmarketplace.asset_elastic_search.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/elastic/author")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {
    AuthorService authorService;

    @GetMapping("")
    public APIResponse<Author> getAuthor(@RequestParam String id) {
        Author author = authorService.getAuthor(id);
        return APIResponse.<Author>builder()
                .result(author)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<Set<Author>> getAllAuthor() {
        Set<Author> allAuthors = authorService.getAllAuthors();
        return APIResponse.<Set<Author>>builder()
                .result(allAuthors)
                .build();
    }

    @KafkaListener(topics = "author", groupId = "group-authors")
    public Void createAuthor(KafkaMessage<Author> message) {
        authorService.consumerAuthor(message);
        return null;
    }

}
