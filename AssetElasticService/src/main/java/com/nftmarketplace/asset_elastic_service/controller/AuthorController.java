package com.nftmarketplace.asset_elastic_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_elastic_service.model.Author;
import com.nftmarketplace.asset_elastic_service.model.dto.APIResponse;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/elastic/author")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {
        AuthorService authorService;

        @GetMapping
        public Mono<APIResponse<Author>> getAuthor(@RequestParam String authorId) {
                return authorService.getAuthor(authorId)
                                .map(author -> APIResponse
                                                .<Author>builder()
                                                .result(author)
                                                .build());
        }

        @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<APIResponse<Author>> getAllAuthors() {
                return authorService.getAllAuthors()
                                .map(author -> APIResponse
                                                .<Author>builder()
                                                .result(author)
                                                .build());
        }

        @GetMapping(path = "/top")
        public Mono<APIResponse<List<Author>>> getMethodName(@RequestParam Integer limit) {
                return authorService.getTopAuthors(limit)
                                .collect(Collectors.toList())
                                .map(author -> APIResponse
                                                .<List<Author>>builder()
                                                .result(author)
                                                .build());
        }

        @GetMapping("/count")
        public Mono<APIResponse<Long>> countAllAuthors() {
                return authorService.countAllAuthors()
                                .map(numberAuthors -> APIResponse
                                                .<Long>builder()
                                                .result(numberAuthors)
                                                .build());
        }
}
