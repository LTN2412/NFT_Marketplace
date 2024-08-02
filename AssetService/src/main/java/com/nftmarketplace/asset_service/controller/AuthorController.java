package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;
import com.nftmarketplace.asset_service.service.AuthorService;
import com.nftmarketplace.asset_service.utils.mapper.AuthorMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/author")
public class AuthorController {
    AuthorService authorService;

    @PostMapping("")
    public APIResponse<Author> createAuthor(@ModelAttribute AuthorRequest request) {
        Author author = authorService.createAuthor(request);
        return APIResponse.<Author>builder()
                .result(author)
                .build();
    }

    @Cacheable(value = "author", key = "#id")
    @GetMapping("")
    public APIResponse<AuthorFlat> getAuthor(@RequestParam String id) {
        AuthorFlat author = AuthorMapper.INSTANCE.toAuthorFlat(authorService.getAuthor(id));
        return APIResponse.<AuthorFlat>builder()
                .result(author)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<Set<AuthorFlat>> getAllAuthors() {
        Set<Author> authors = authorService.getAllAuthors();
        Set<AuthorFlat> authorsFlat = AuthorMapper.INSTANCE.toAuthorsFlat(authors);
        return APIResponse.<Set<AuthorFlat>>builder()
                .result(authorsFlat)
                .build();
    }

    @CacheEvict(value = "author", key = "#id")
    @PutMapping("")
    public APIResponse<Author> updateAuthor(@RequestParam String id, @RequestBody AuthorRequest request) {
        Author updateAuthor = authorService.updateAuthor(id, request);
        return APIResponse.<Author>builder()
                .result(updateAuthor)
                .build();
    }

    @CacheEvict(value = "author", key = "#id")
    @DeleteMapping("")
    public APIResponse<Void> deleteAuthor(@RequestParam String id) {
        authorService.deleteAuthor(id);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }
}