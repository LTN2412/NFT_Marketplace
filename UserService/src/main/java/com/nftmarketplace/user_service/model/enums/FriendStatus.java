package com.nftmarketplace.user_service.model.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FriendStatus {
    NOT_FRIEND("not_friend", "Not friend!"),
    WAITING("waiting", "Send request completed!"),
    ACCEPTED("accepted", "Add friend completed!"),
    REJECTED("rejected", "Reject friend completed!");

    String status;
    String message;
}
