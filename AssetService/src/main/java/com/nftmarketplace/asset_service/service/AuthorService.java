package com.nftmarketplace.asset_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_service.configuration.exception.AppException;
import com.nftmarketplace.asset_service.configuration.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.kafka.KafkaMessage;
import com.nftmarketplace.asset_service.model.dto.request.AuthorElastic;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.repository.AssetRepository;
import com.nftmarketplace.asset_service.repository.AuthorRepository;
import com.nftmarketplace.asset_service.utils.mapper.AuthorMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {
    AuthorRepository authorRepository;
    AssetRepository assetRepository;
    CloudinaryService cloudinaryService;
    KafkaTemplate<String, Object> kafkaTemplate;

    public Author createAuthor(AuthorRequest request) {
        if (authorRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        // Mapper
        Author author = AuthorMapper.INSTANCE.toAuthor(request);
        // Create Id
        String id = UUID.randomUUID().toString();
        author.setId(id);
        // Create assets if exists
        if (request.getAssets() != null) {
            List<Asset> assets = assetRepository.findAllById(request.getAssets());
            author.setAssets(new HashSet<>(assets));
        }
        // Create avatarPath and coverImgPath if exists
        if (request.getAvatar() != null) {
            String avatarPath = cloudinaryService.uploadImg(request.getAvatar(), id, "avatar_");
            author.setAvatarPath(avatarPath);
        }
        if (request.getCoverImg() != null) {
            String coverImgPath = cloudinaryService.uploadImg(request.getCoverImg(), id, "cover_");
            author.setCoverImgPath(coverImgPath);
        }
        Author saveAuthor = authorRepository.save(author);
        AuthorElastic authorElastic = AuthorMapper.INSTANCE.toAuthorElastic(saveAuthor);
        authorElastic.setAssetIds(request.getAssets());

        KafkaMessage<AuthorElastic> kafkaMessage = KafkaMessage.<AuthorElastic>builder()
                .action("CREATE")
                .data(authorElastic)
                .build();
        kafkaTemplate.send("author", kafkaMessage);
        return saveAuthor;
    }

    public Author getAuthor(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        return author;
    }

    public Set<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return new HashSet<Author>(authors);
    }

    public Author updateAuthor(String id, AuthorRequest request) {
        if (request.getAssets() != null)
            throw new AppException(ErrorCode.UNKNOW_ERROR);
        Author oldAuthor = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Author author = AuthorMapper.INSTANCE.toAuthor(request, oldAuthor);
        Author saveAuthor = authorRepository.save(author);
        AuthorElastic authorElastic = AuthorMapper.INSTANCE.toAuthorElastic(saveAuthor);
        authorElastic
                .setAssetIds(author.getAssets().stream().map((asset) -> asset.getId()).collect(Collectors.toSet()));
        KafkaMessage<AuthorElastic> message = KafkaMessage.<AuthorElastic>builder()
                .action("UPDATE")
                .data(authorElastic)
                .build();
        kafkaTemplate.send("author", message);
        return saveAuthor;
    }

    public Void deleteAuthor(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        authorRepository.delete(author);
        AuthorElastic authorElastic = new AuthorElastic();
        authorElastic.setId(id);
        KafkaMessage<AuthorElastic> message = KafkaMessage.<AuthorElastic>builder()
                .action("DELETE")
                .data(authorElastic)
                .build();
        kafkaTemplate.send("author", message);
        return null;
    }
}
