import { queryOptions } from "@tanstack/react-query";

import {
  CountMessageNotSeenAPI,
  FetchAllMessagesFrom1UserAPI,
} from "../query/NotificationAPI";

export const CountAllMessagesNotSeen = () => {
  return queryOptions({
    queryKey: ["notification_not_seen"],
    queryFn: async () => CountMessageNotSeenAPI(),
  });
};

export const GetAllMessagesFrom1User = () => {
  return queryOptions({
    queryKey: ["all_notification"],
    queryFn: async () => FetchAllMessagesFrom1UserAPI(),
  });
};
