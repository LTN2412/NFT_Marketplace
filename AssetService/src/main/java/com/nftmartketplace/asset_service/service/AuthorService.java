package com.nftmartketplace.asset_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nftmartketplace.asset_service.configuration.exception.AppException;
import com.nftmartketplace.asset_service.configuration.exception.ErrorCode;
import com.nftmartketplace.asset_service.model.Asset;
import com.nftmartketplace.asset_service.model.Author;
import com.nftmartketplace.asset_service.model.dto.kafka.KafkaMessage;
import com.nftmartketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmartketplace.asset_service.repository.AssetRepository;
import com.nftmartketplace.asset_service.repository.AuthorRepository;
import com.nftmartketplace.asset_service.utils.mapper.AuthorMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {
    AuthorRepository authorRepository;
    AssetRepository assetRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public Author createAuthor(AuthorRequest request) {
        if (authorRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Author author = AuthorMapper.INSTANCE.toAuthor(request);
        if (request.getAssets() != null) {
            List<Asset> assets = assetRepository.findAllById(request.getAssets());
            author.setAssets(new HashSet<>(assets));
        }
        Author saveAuthor = authorRepository.save(author);
        KafkaMessage<Author> kafkaMessage = KafkaMessage.<Author>builder()
                .action("CREATE")
                .data(saveAuthor)
                .build();
        kafkaTemplate.send("author", kafkaMessage);
        return saveAuthor;
    }

    @Cacheable(value = "author", key = "#id")
    public Author getAuthor(String id) {
        Author author = AuthorMapper.INSTANCE
                .toAuthor(authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED)));
        return author;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author updateAuthor(String id, AuthorRequest request) {
        if (!authorRepository.existsById(id))
            throw new AppException(ErrorCode.NOT_EXISTED);
        Author author = AuthorMapper.INSTANCE
                .toAuthor(request);
        author.setId(id);
        if (request.getAssets() != null) {
            List<Asset> assets = assetRepository.findAllById(request.getAssets());
            author.setAssets(new HashSet<>(assets));
        }
        Author saveAuthor = authorRepository.save(author);
        KafkaMessage<Author> message = KafkaMessage.<Author>builder()
                .action("UPDATE")
                .data(saveAuthor)
                .build();
        kafkaTemplate.send("author", message);
        return saveAuthor;
    }

    public Void deleteAuthor(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        authorRepository.delete(author);
        KafkaMessage<Author> message = KafkaMessage.<Author>builder()
                .action("DELETE")
                .data(Author.builder()
                        .id(id)
                        .build())
                .build();
        kafkaTemplate.send("author", message);
        return null;
    }

}
