package com.marketplace.notification_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.marketplace.notification_service.model.Message;
import com.marketplace.notification_service.model.dto.APIResponse;
import com.marketplace.notification_service.service.MessageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;

    @GetMapping
    public Mono<APIResponse<List<Message>>> getAllMessagesFrom1User(@AuthenticationPrincipal Jwt jwt) {
        return messageService.getAllMessagesFrom1User(jwt.getSubject()).collect(Collectors.toList()).map(
                messages -> APIResponse.<List<Message>>builder()
                        .result(messages)
                        .build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/allRequest")
    public Flux<Message> getAllMessageRequestFrom1User(@AuthenticationPrincipal Jwt jwt) {
        return messageService.getAllMessageRequestFrom1User(jwt.getSubject());
    }

    @GetMapping("/allReceive")
    public Flux<Message> getAllMessageReceiveFrom1User(@AuthenticationPrincipal Jwt jwt) {
        return messageService.getAllMessageReceiveFrom1User(jwt.getSubject());
    }

    @GetMapping("/count")
    public Mono<APIResponse<Long>> getMethodName(@AuthenticationPrincipal Jwt jwt) {
        return messageService.getNumberMessagesNotSeen(jwt.getSubject()).map(
                number -> APIResponse.<Long>builder()
                        .result(number)
                        .build());
    }

    @PutMapping("/isSeen")
    public Mono<APIResponse<?>> updateIsSeen(@RequestParam String messageId) {
        return messageService.updateIsSeen(messageId).map(
                message -> APIResponse.builder()
                        .message(message)
                        .build());
    }
}
