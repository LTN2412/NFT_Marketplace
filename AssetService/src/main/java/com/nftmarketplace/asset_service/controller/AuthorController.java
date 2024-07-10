package com.nftmarketplace.asset_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.APIResponse;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/asset/author")
public class AuthorController {
    AuthorService authorService;

    @PostMapping("")
    public APIResponse<Author> createAuthor(@RequestBody AuthorRequest request) {
        Author author = authorService.createAuthor(request);
        return APIResponse.<Author>builder()
                .result(author)
                .build();
    }

    @GetMapping("")
    public APIResponse<Author> getAuthor(@RequestParam String id) {
        Author author = authorService.getAuthor(id);
        return APIResponse.<Author>builder()
                .result(author)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return APIResponse.<List<Author>>builder()
                .result(authors)
                .build();
    }

    @PutMapping("")
    public APIResponse<Author> postMethodName(@RequestParam String id, @RequestBody AuthorRequest request) {
        Author updateAuthor = authorService.updateAuthor(id, request);
        return APIResponse.<Author>builder()
                .result(updateAuthor)
                .build();
    }

    @DeleteMapping("")
    public APIResponse<Void> deleteAuthor(@RequestParam String id) {
        authorService.deleteAuthor(id);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }

}