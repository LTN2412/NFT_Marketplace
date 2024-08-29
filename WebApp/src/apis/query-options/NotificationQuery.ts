import { queryOptions } from "@tanstack/react-query";
import {
  CountMessageNotSeenAPI,
  FetchAllMessagesFrom1UserAPI,
} from "../query/NotificationAPI";

export const CountAllMessagesNotSeen = (userId: string) => {
  return queryOptions({
    queryKey: ["notification_not_seen"],
    queryFn: async () => CountMessageNotSeenAPI(userId),
  });
};

export const GetAllMessagesFrom1User = (userId: string) => {
  return queryOptions({
    queryKey: ["all_notification"],
    queryFn: async () => FetchAllMessagesFrom1UserAPI(userId),
  });
};
