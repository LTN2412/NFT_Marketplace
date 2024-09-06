import { z } from "zod";

export const UpdateUserSchema = z.object({
  name: z.string().min(1, { message: "Name is required" }),
  gender: z.string().min(1, { message: "Gender is required" }),
  address: z.string().min(1, { message: "Address is required" }),
  phoneNumber: z.string().min(1, { message: "Phone number is required" }),
});

export type FormDataUserUpdate = {
  name: string;
  gender: string;
  address: string;
  phoneNumber: string;
};
