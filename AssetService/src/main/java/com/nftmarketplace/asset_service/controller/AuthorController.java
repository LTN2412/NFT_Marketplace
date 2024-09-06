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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    public APIResponse<Author> createAuthor(@ModelAttribute AuthorRequest request) {
        Author author = authorService.createAuthor(request);
        return APIResponse.<Author>builder()
                .result(author)
                .build();
    }

    @Cacheable(value = "author", key = "#authorId")
    @GetMapping
    public APIResponse<AuthorFlat> getAuthorId(@RequestParam String authorId) {
        AuthorFlat authorFlat = authorService.getAuthorFlat(authorId);
        return APIResponse.<AuthorFlat>builder()
                .result(authorFlat)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/all")
    public APIResponse<List<AuthorFlat>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        List<AuthorFlat> authorsFlat = AuthorMapper.INSTANCE.toAuthorFlatList(authors);
        return APIResponse.<List<AuthorFlat>>builder()
                .result(authorsFlat)
                .build();
    }

    @CacheEvict(value = "author", key = "#authorId")
    @PutMapping
    public APIResponse<Author> updateAuthor(@RequestParam String authorId, @ModelAttribute AuthorRequest request) {
        Author updateAuthor = authorService.updateAuthor(authorId, request);
        return APIResponse.<Author>builder()
                .result(updateAuthor)
                .build();
    }

    @CacheEvict(value = "author", key = "#authorId")
    @DeleteMapping
    public APIResponse<Void> deleteAuthor(@RequestParam String authorId) {
        authorService.deleteAuthor(authorId);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }
}