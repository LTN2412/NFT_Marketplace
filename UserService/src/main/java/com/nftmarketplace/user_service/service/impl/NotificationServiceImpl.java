// package com.nftmarketplace.user_service.service.impl;

// import org.springframework.stereotype.Service;

// import com.nftmarketplace.user_service.model.enums.NotificationStatus;
// import com.nftmarketplace.user_service.model.node.Notification;
// import com.nftmarketplace.user_service.repository.NotifitcationRepository;
// import com.nftmarketplace.user_service.service.NotificationService;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;

// @Service
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class NotificationServiceImpl implements NotificationService {

// NotifitcationRepository notifitcationRepository;

// @Override
// public Mono<Integer> createAddFriendNotification(String userId1, String
// userId2) {
// Notification notification = Notification.builder()
// .message(String.format("Use receive friend request from %s", userId1))
// .status(NotificationStatus.WAITING_ACCEPT)
// .build();
// return notifitcationRepository.save(notification).flatMap(notificationId -> {
// return notifitcationRepository.sendFriendRequest(userId1, userId2,
// notificationId.getId());
// });
// }

// @Override
// public Flux<String> getAllNotificationsFrom1User(String userId) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method
// 'getAllNotificationsFrom1User'");
// }

// @Override
// public Mono<String> updateNotificaton(String userId) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method
// 'updateNotificaton'");
// }
// }
