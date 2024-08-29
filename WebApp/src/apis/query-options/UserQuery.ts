import { queryOptions } from "@tanstack/react-query";
import { FetchCartUserAPI, FetchUserAPI } from "../query/UserAPI";

export const GetUserById = (userId: string) => {
  return queryOptions({
    queryKey: ["user"],
    queryFn: async () => FetchUserAPI(userId),
  });
};

export const GetCartUserById = (userId: string) => {
  return queryOptions({
    queryKey: ["cart"],
    queryFn: async () => FetchCartUserAPI(userId),
  });
};
