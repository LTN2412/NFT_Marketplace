import { APIResponse, CountResponse } from "@/types/APIResponse.type";

import { MessagesResponse } from "@/types/Message.type";
import { httpNotification } from "@/utils/Http";

export const CountMessageNotSeenAPI = async () => {
  return httpNotification.get<CountResponse>("/count");
};

export const FetchAllMessagesFrom1UserAPI = async () => {
  return httpNotification.get<MessagesResponse>("");
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
