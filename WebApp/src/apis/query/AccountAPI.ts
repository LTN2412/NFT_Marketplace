import { TokenResponse } from "@/types/Account.type";
import { FormDataSignUp, FormDataSignIn } from "@/types/schema/SignUp";
import { httpAccount } from "@/utils/Http";

export const FetchTokenAPI = async (data: FormDataSignIn) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
  }
  return httpAccount.get<TokenResponse>("/identity/token", { data: formData });
};

export const CreateAccountAPI = async (data: FormDataSignUp) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
    formData.delete("verifyPassword");
  }
  return httpAccount.get<TokenResponse>("/identity/token", { data: formData });
};
