// package com.nftmarketplace.user_service.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.nftmarketplace.user_service.model.node.Notification;
// import com.nftmarketplace.user_service.service.NotificationService;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import reactor.core.publisher.Mono;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @RestController
// @RequestMapping("/notification")
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class NotificationController {
// NotificationService notificationService;

// @PostMapping
// public Mono<Integer> createNotification(@RequestParam String userId1, String
// userId2) {
// return notificationService.createAddFriendNotification(userId1, userId2);
// }
// }
