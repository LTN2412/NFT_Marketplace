import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";

import SignInSchema, { FormDataSignIn } from "@/types/schema/SignUp";
import { FetchTokenAPI } from "@/apis/query/AccountAPI";

import UserIccon from "@/assets/User.svg?react";
import LockIccon from "@/assets/Lock.svg?react";

export default function SignIn() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm({
    defaultValues: {
      username: "",
      password: "",
    },
    resolver: zodResolver(SignInSchema),
  });
  const [isPending, isTransition] = useState(false);

  const { mutate } = useMutation({
    mutationFn: (data: FormDataSignIn) => FetchTokenAPI(data),
    onMutate: () => isTransition(true),
    onSettled: () => isTransition(false),
    onSuccess: () => {
      alert("Success");
      window.location.href = "/";
    },
    onError: (error) => {
      setError("root", {
        message: error.message,
      });
    },
  });

  const onSubmit = (formData: FormDataSignIn) => mutate(formData);
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="flex flex-col gap-3 bg-background text-black">
        <Input
          placeholder="Username"
          StartIcon={<UserIccon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("username")}
        />
        <p className="text-purple">{errors.username?.message}</p>
        <Input
          type="password"
          placeholder="Password"
          StartIcon={<LockIccon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("password")}
        />
        <p className="text-purple">{errors.password?.message}</p>
        <Button
          type="submit"
          className={`hover: h-14 w-80 rounded-3xl bg-purple hover:bg-purple ${isPending ? "bg-red-600" : ""}`}
        >
          Create Account
        </Button>
      </div>
    </form>
  );
}
