import { queryOptions } from "@tanstack/react-query";
import { FetchTokenAPI } from "../query/AccountAPI";
import { FormDataSignIn } from "@/types/schema/SignUp";

export const GetToken = (data: FormDataSignIn) => {
  return queryOptions({
    queryKey: ["token"],
    queryFn: async () => FetchTokenAPI(data),
  });
};
