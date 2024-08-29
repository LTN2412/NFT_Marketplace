package com.marketplace.notification_service.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import reactor.core.publisher.Mono;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class WebSocketHandlerImpl implements WebSocketHandler {
    Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String userId = session.getHandshakeInfo().getUri().getQuery().split("=")[1];
        sessions.put(userId, session);
        return session.receive()
                .doFinally(_ -> sessions.remove(userId))
                .then();
    }

    public void sendMessageToUser(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.send(Mono.just(session.textMessage(message))).subscribe();
        }
    }
}