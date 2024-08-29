package com.marketplace.notification_service.event.consumer;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import com.marketplace.notification_service.model.kafkaModel.RequestKafka;
import com.marketplace.notification_service.service.MessageService;
import com.marketplace.notification_service.utils.mapper.MessageMapper;
import com.marketplace.notification_service.ws.WebSocketHandlerImpl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings("unused")
public class EventConsumer {
    Gson gson = new Gson();
    MessageService messageService;
    WebSocketHandlerImpl webSocketHandler;

    public EventConsumer(ReceiverOptions<String, String> receiverOptions,
            MessageService messageService, WebSocketHandlerImpl webSocketHandler) {
        this.messageService = messageService;
        this.webSocketHandler = webSocketHandler;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("request")))
                .receive()
                .flatMap(message -> {
                    RequestKafka request = gson.fromJson(message.value(), RequestKafka.class);
                    switch (request.getMessageType()) {
                        case ACCEPT_FRIEND:
                            return messageService.updateMessage(request);
                        case REJECT_FRIEND:
                            return messageService.updateMessage(request);
                        default:
                            return messageService.createMessage(MessageMapper.INSTANCE.toMessage(request))
                                    .doOnSuccess(a -> webSocketHandler.sendMessageToUser(request.getUserReceiveId(),
                                            "User " + request.getUserReceiveId() + " have new message"));
                    }
                })
                .subscribe();
    }
}
