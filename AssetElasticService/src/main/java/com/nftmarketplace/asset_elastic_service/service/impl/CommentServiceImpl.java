package com.nftmarketplace.asset_elastic_service.service.impl;

import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_elastic_service.exception.AppException;
import com.nftmarketplace.asset_elastic_service.exception.ErrorCode;
import com.nftmarketplace.asset_elastic_service.model.Comment;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.CommentKafka;
import com.nftmarketplace.asset_elastic_service.service.CommentService;
import com.nftmarketplace.asset_elastic_service.utils.mapper.CommentMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.nftmarketplace.asset_elastic_service.repository.CommentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    @Override
    public Mono<Void> consumeComment(CommentKafka commentKafka) {
        return switch (commentKafka.getAction()) {
            case CREATE, UPDATE -> commentRepository.save(CommentMapper.INSTANCE.toComment(commentKafka)).then();
            case DELETE -> commentRepository.deleteById(commentKafka.getId());
            default -> Mono.empty();
        };
    }

    @Override
    public Mono<Comment> getComment(String commentId) {
        return checkExistComments(commentId).then(Mono.defer(() -> commentRepository.findById(commentId)));
    }

    @Override
    public Flux<Comment> getAllByAssetIdOrderByUpdatedAt(String assetId) {
        return commentRepository.findAllByAssetIdOrderByUpdatedAt(assetId);
    }

    @Override
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<Void> checkExistComments(String... commentIds) {
        return Flux.fromArray(commentIds)
                .flatMap(commentId -> commentRepository.existsById(commentId)
                        .flatMap(exist -> exist ? Mono.empty()
                                : Mono.error(
                                        new AppException(ErrorCode.EXISTED, "CommentId " + commentId + " not exist"))))
                .then();
    }
}
