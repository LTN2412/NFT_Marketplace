import { queryOptions } from "@tanstack/react-query";

import {
  CheckFollowStatusAPI,
  FetchCartUserAPI,
  FetchUserAPI,
} from "../query/UserAPI";

export const GetUserById = () => {
  return queryOptions({
    queryKey: ["user"],
    queryFn: async () => FetchUserAPI(),
  });
};

export const GetCartUserById = () => {
  return queryOptions({
    queryKey: ["cart"],
    queryFn: async () => FetchCartUserAPI(),
  });
};

export const CheckFollowStatus = (userId: string) => {
  return queryOptions({
    queryKey: [""],
    queryFn: async () => CheckFollowStatusAPI(userId),
  });
};
