import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";

import { CreateAccountAPI, FetchTokenAPI } from "@/apis/query/AccountAPI";
import EmailIcon from "@/assets/Email.svg?react";
import EyeCloseIcon from "@/assets/EyeClose.svg?react";
import EyeOpenIcon from "@/assets/EyeOpen.svg?react";
import LockIccon from "@/assets/Lock.svg?react";
import UserIccon from "@/assets/User.svg?react";
import { FormDataSignIn } from "@/types/schema/SignInSchema";
import { FormDataSignUp, SignUpSchema } from "@/types/schema/SignUpSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

export default function SignUp() {
  const {
    register,
    getValues,
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
  const [isPending, setIsPending] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showVerifyPassword, setShowVerifyPassword] = useState(false);
  const navigate = useNavigate();

  const createAccountMutation = useMutation({
    mutationFn: (data: FormDataSignUp) => CreateAccountAPI(data),
    onMutate: () => setIsPending(true),
    onSettled: () => setIsPending(false),
    onSuccess: () => {
      const { username, password } = getValues();
      fetchTokenMutation.mutate({ username, password });
    },
    onError: (error) => {
      console.log(error);
      setError("root", {
        message: "Failed to create account. Please try again.",
      });
    },
  });

  const fetchTokenMutation = useMutation({
    mutationFn: (data: FormDataSignIn) => FetchTokenAPI(data),
    onSuccess: () => {
      navigate("/info");
    },
    onError: (error) => {
      console.log(error);
      setError("root", {
        message:
          "Account created, but failed to sign in. Please try signing in manually.",
      });
    },
  });

  const onSubmit = (formData: FormDataSignUp) => {
    if (formData.password !== formData.verifyPassword) {
      setError("verifyPassword", {
        message: "Passwords don't match",
      });
    } else {
      createAccountMutation.mutate(formData);
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleVerifyPasswordVisibility = () => {
    setShowVerifyPassword(!showVerifyPassword);
  };

  return (
    <div className="flex flex-col gap-5 px-12 py-6">
      <h3>Create Account</h3>
      <p>
        Welcome! enter your details and start creating, collecting and selling
        NFTs.
      </p>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="flex flex-col gap-3 bg-background text-black lg:w-80">
          <Input
            placeholder="Username"
            StartIcon={<UserIccon className="ml-4 w-8 fill-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("username")}
          />
          <p className="text-purple">{errors.username?.message}</p>
          <Input
            type="email"
            placeholder="Email Address"
            StartIcon={<EmailIcon className="ml-4 w-8 fill-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("email")}
          />
          <p className="text-purple">{errors.email?.message}</p>
          <div className="relative">
            <Input
              type={showPassword ? "text" : "password"}
              placeholder="Password"
              StartIcon={<LockIccon className="ml-4 w-8 fill-gray" />}
              EndIcon={
                <button
                  type="button"
                  onClick={togglePasswordVisibility}
                  className="absolute right-4 top-1/2 -translate-y-1/2"
                >
                  {showPassword ? (
                    <EyeOpenIcon className="w-6 stroke-gray" />
                  ) : (
                    <EyeCloseIcon className="w-6 fill-gray" />
                  )}
                </button>
              }
              className="h-14 rounded-3xl pl-14 pr-14"
              {...register("password")}
            />
          </div>
          <p className="text-purple">{errors.password?.message}</p>
          <div className="relative">
            <Input
              type={showVerifyPassword ? "text" : "password"}
              placeholder="Verify Password"
              StartIcon={<LockIccon className="ml-4 w-8 fill-gray" />}
              EndIcon={
                <button
                  type="button"
                  onClick={toggleVerifyPasswordVisibility}
                  className="absolute right-4 top-1/2 -translate-y-1/2"
                >
                  {showVerifyPassword ? (
                    <EyeOpenIcon className="w-6 stroke-gray" />
                  ) : (
                    <EyeCloseIcon className="w-6 fill-gray" />
                  )}
                </button>
              }
              className="h-14 rounded-3xl pl-14 pr-14"
              {...register("verifyPassword")}
            />
          </div>
          <p className="text-purple">{errors.verifyPassword?.message}</p>
          <Button
            type="submit"
            className={`hover: h-14 rounded-3xl bg-purple hover:bg-purple ${isPending ? "scale-95 opacity-80" : ""}`}
          >
            Create Account
          </Button>
          <p className="text-purple">{errors.root?.message}</p>
          <p className="font-semibold text-foreground">
            {"Have account? "}
            <Link to={"../signin"} className="text-purple">
              Sign in
            </Link>
            {" now"}
          </p>
        </div>
      </form>
    </div>
  );
}
