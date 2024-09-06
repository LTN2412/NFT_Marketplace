import { z } from "zod";

export const SignUpSchema = z.object({
  username: z.string().min(1, {
    message: "Username is required",
  }),
  password: z.string().min(1, {
    message: "Password is required",
  }),
  verifyPassword: z.string().min(1, {
    message: "Verify Password is required",
  }),
  email: z.string().email("Not format email"),
});

export type FormDataSignUp = {
  username: string;
  email: string;
  password: string;
  verifyPassword: string;
};
