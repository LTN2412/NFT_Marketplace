import { APIResponse, CountResponse } from "@/types/APIResponse.type";

import { MessagesResponse } from "@/types/Message.type";
import { httpNotification } from "@/utils/Http";

export const CountMessageNotSeenAPI = async (userId: string) => {
  return httpNotification.get<CountResponse>("/count", {
    params: {
      userId: userId,
    },
  });
};

export const FetchAllMessagesFrom1UserAPI = async (userId: string) => {
  return httpNotification.get<MessagesResponse>("", {
    params: {
      userId: userId,
    },
  });
};

export const UpdateIsSeenMessageAPI = async (messageId: string) => {
  {
    return httpNotification.put<APIResponse>("/isSeen", null, {
      params: {
        messageId: messageId,
      },
    });
  }
};
