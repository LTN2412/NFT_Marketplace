import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FetchTokenAPI } from "@/apis/query/AccountAPI";
import EyeCloseIcon from "@/assets/EyeClose.svg?react";
import EyeOpenIcon from "@/assets/EyeOpen.svg?react";
import LockIccon from "@/assets/Lock.svg?react";
import UserIccon from "@/assets/User.svg?react";
import { FormDataSignIn, SignInSchema } from "@/types/schema/SignInSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

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
  const [isPending, setIsTransition] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: (data: FormDataSignIn) => FetchTokenAPI(data),
    onMutate: () => {
      setIsTransition(true);
    },
    onSettled: () => setIsTransition(false),
    onSuccess: () => {
      navigate("/");
    },
    onError: () => {
      setError("root", {
        message: "Something went wrong!",
      });
    },
  });

  const onSubmit = (formData: FormDataSignIn) => mutate(formData);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="flex flex-col gap-5 px-12 py-6 lg:pt-24">
      <h3>Login</h3>
      <p>Welcome back! Continue exploring, collecting, and selling NFTs.</p>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="flex flex-col gap-3 bg-background text-black lg:w-80">
          <Input
            placeholder="Username"
            StartIcon={<UserIccon className="ml-4 w-8 fill-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("username")}
          />
          <p className="text-purple">{errors.username?.message}</p>
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
          <Button
            type="submit"
            className={`hover: h-14 rounded-3xl bg-purple hover:bg-purple ${isPending ? "scale-95 opacity-80" : ""} `}
          >
            Sign In
          </Button>
          <p className="text-purple">{errors.root?.message}</p>
        </div>
      </form>
    </div>
  );
}
