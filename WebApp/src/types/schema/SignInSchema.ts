import { z } from "zod";

export const SignInSchema = z.object({
  username: z.string().min(1, {
    message: "Username is required",
  }),
  password: z.string().min(1, {
    message: "Password is required",
  }),
});

export type FormDataSignIn = {
  username: string;
  password: string;
};
