import { APIResponse } from "@/types/APIResponse.type";
import { AccountResponse } from "@/types/Account.type";
import { FormDataSignIn } from "@/types/schema/SignInSchema";
import { FormDataSignUp } from "@/types/schema/SignUpSchema";

import { httpAccount } from "@/utils/Http";

export const FetchTokenAPI = async (data: FormDataSignIn) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
  }
  return httpAccount.post<APIResponse>("/token/cookie", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const CreateAccountAPI = async (data: FormDataSignUp) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
    formData.delete("verifyPassword");
    formData.append("roles", "ADMIN");
  }
  return httpAccount.post<AccountResponse>("/account/register", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
