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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MessageService messageService;

    @GetMapping
    public Mono<APIResponse<List<Message>>> getAllMessagesFrom1User(@RequestParam String userId) {
        return messageService.getAllMessagesFrom1User(userId).collect(Collectors.toList()).map(
                messages -> APIResponse.<List<Message>>builder()
                        .result(messages)
                        .build());
    }

    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/allRequest")
    public Flux<Message> getAllMessageRequestFrom1User(@RequestParam String userId) {
        return messageService.getAllMessageRequestFrom1User(userId);
    }

    @GetMapping("/allReceive")
    public Flux<Message> getAllMessageReceiveFrom1User(@RequestParam String userId) {
        return messageService.getAllMessageReceiveFrom1User(userId);
    }

    @GetMapping("/count")
    public Mono<APIResponse<Long>> getMethodName(@RequestParam String userId) {
        return messageService.getNumberMessagesNotSeen(userId).map(
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
