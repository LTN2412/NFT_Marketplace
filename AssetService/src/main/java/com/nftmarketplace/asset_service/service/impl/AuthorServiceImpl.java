package com.nftmarketplace.asset_service.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.asset_service.exception.AppException;
import com.nftmarketplace.asset_service.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;
import com.nftmarketplace.asset_service.model.enums.Action;
import com.nftmarketplace.asset_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_service.repository.AuthorRepository;
import com.nftmarketplace.asset_service.service.AuthorService;
import com.nftmarketplace.asset_service.service.CloudinaryService;
import com.nftmarketplace.asset_service.utils.mapper.AuthorMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorServiceImpl implements AuthorService {
    AuthorRepository authorRepository;
    CloudinaryService cloudinaryService;
    KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Override
    public Author createAuthor(AuthorRequest request) {
        // * Check and map author
        if (authorRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Author author = AuthorMapper.INSTANCE.toAuthor(request);
        // * Create id
        String authorId = UUID.randomUUID().toString();
        author.setId(authorId);
        // * Create avatarPath and coverImgPath if exists
        if (request.getAvatar() != null) {
            String avatarPath = cloudinaryService.uploadImg(request.getAvatar(), authorId, "avatar_");
            author.setAvatarPath(avatarPath);
        }
        if (request.getCoverImg() != null) {
            String coverImgPath = cloudinaryService.uploadImg(request.getCoverImg(), authorId, "cover_");
            author.setCoverImgPath(coverImgPath);
        }
        // * Save
        Author saveAuthor = authorRepository.save(author);
        // * Send Kafka
        AuthorKafka authorKafka = AuthorMapper.INSTANCE.toAuthorKafka(saveAuthor);
        authorKafka.setAction(Action.CREATE);
        kafkaTemplate.send("author", gson.toJson(authorKafka));
        return saveAuthor;
    }

    @Override
    public Author createAuthorFromKafka(AuthorKafka authorKafka) {
        // * Check if author already exists
        if (authorRepository.existsById(authorKafka.getId()))
            throw new AppException(ErrorCode.EXISTED);
        // * Map AuthorKafka to Author
        Author author = AuthorMapper.INSTANCE.toAuthor(authorKafka);
        // * Check if name is null
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        // * Save the author
        return authorRepository.save(author);
    }

    @Override
    public AuthorFlat getAuthorFlat(String authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        AuthorFlat authorFlat = AuthorMapper.INSTANCE.toAuthorFlat(author);
        return authorFlat;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    @Override
    public Author updateAuthor(String authorId, AuthorRequest request) {
        // * Get old author
        Author oldAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Author author = AuthorMapper.INSTANCE.toAuthor(request, oldAuthor);
        // * Save
        Author saveAuthor = authorRepository.save(author);
        // * Send Kafka
        AuthorKafka authorKafka = AuthorMapper.INSTANCE.toAuthorKafka(saveAuthor);
        authorKafka.setAction(Action.UPDATE);
        kafkaTemplate.send("author", gson.toJson(authorKafka));
        return saveAuthor;
    }

    @Override
    public Void deleteAuthor(String authorId) {
        // * Check author is exits
        authorRepository.findById(authorId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        // * Delete author
        authorRepository.deleteById(authorId);
        // * Send Kafka
        AuthorKafka authorKafka = new AuthorKafka();
        authorKafka.setId(authorId);
        authorKafka.setAction(Action.DELETE);
        kafkaTemplate.send("author", gson.toJson(authorKafka));
        return null;
    }
}
