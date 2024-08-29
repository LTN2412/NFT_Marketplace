import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { FormDataSignUp, SignUpSchema } from "@/types/schema/SignUp";

import { CreateAccountAPI } from "@/apis/query/AccountAPI";

import UserIccon from "@/assets/User.svg?react";
import EmailIcon from "@/assets/Email.svg?react";
import LockIccon from "@/assets/Lock.svg?react";

export default function SignUp() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm({
    defaultValues: {
      username: "",
      password: "",
      verifyPassword: "",
      email: "",
    },
    resolver: zodResolver(SignUpSchema),
  });
  const [isPending, isTransition] = useState(false);

  const { mutate } = useMutation({
    mutationFn: (data: FormDataSignUp) => CreateAccountAPI(data),
    onMutate: () => isTransition(true),
    onSettled: () => isTransition(false),
    onSuccess: () => {
      alert("Success");
      window.location.href = "/";
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const onSubmit = (formData: FormDataSignUp) => {
    if (formData.password != formData.verifyPassword) {
      setError("verifyPassword", {
        message: "Password didn't match",
      });
    } else {
      mutate(formData);
    }
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="flex flex-col gap-2 bg-background text-black">
        <Input
          placeholder="Username"
          StartIcon={<UserIccon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("username")}
        />
        <p className="text-purple">{errors.username?.message}</p>
        <Input
          type="email"
          placeholder="Email Address"
          StartIcon={<EmailIcon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("email")}
        />
        <p className="text-purple">{errors.email?.message}</p>
        <Input
          type="password"
          placeholder="Password"
          StartIcon={<LockIccon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("password")}
        />
        <p className="text-purple">{errors.password?.message}</p>
        <Input
          type="password"
          placeholder="Verify Password"
          StartIcon={<LockIccon className="ml-4 w-8 fill-gray" />}
          className="h-14 w-80 rounded-3xl pl-14"
          {...register("verifyPassword")}
        />
        <p className="text-purple">{errors.verifyPassword?.message}</p>
        <Button
          type="submit"
          className={`hover: h-14 w-80 rounded-3xl bg-purple hover:bg-purple ${isPending ? "bg-red-600" : ""}`}
        >
          Create Account
        </Button>
        <p className="text-foreground">
          {"Have account? "}
          <Link to={"../signin"} className="text-purple">
            Sign in
          </Link>
          {" now"}
        </p>
      </div>
    </form>
  );
}
