package com.nftmarketplace.user_service.model.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FriendStatus {
    NOT_FRIEND("Not friend!"),
    WAITING("Send request completed!"),
    ACCEPTED("Add friend completed!"),
    REJECTED("Reject friend completed!");

    String message;
}
