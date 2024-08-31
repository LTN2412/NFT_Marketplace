import { queryOptions } from "@tanstack/react-query";
import { FetchCartUserAPI, FetchUserAPI } from "../query/UserAPI";

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
