import { APIResponse } from "@/types/APIResponse.type";
import { FormDataSignUp, FormDataSignIn } from "@/types/schema/SignUp";
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
  }
  return httpAccount.get<APIResponse>("/identity/token", { data: formData });
};
