import { FormDataSignIn } from "@/types/schema/SignInSchema";
import { queryOptions } from "@tanstack/react-query";

import { FetchTokenAPI } from "../query/AccountAPI";

export const GetToken = (data: FormDataSignIn) => {
  return queryOptions({
    queryKey: ["token"],
    queryFn: async () => FetchTokenAPI(data),
  });
};
