import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { UpdateUserInfoAPI } from "@/apis/query/UserAPI";
import AddressIcon from "@/assets/Address.svg?react";
import FemaleIcon from "@/assets/Female.svg?react";
import MaleIcon from "@/assets/Male.svg?react";
import PhoneIcon from "@/assets/Phone.svg?react";
import UserIcon from "@/assets/User.svg?react";
import {
  FormDataUserUpdate,
  UpdateUserSchema,
} from "@/types/schema/UpdateUserSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";

export default function Info() {
  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    setError,
  } = useForm<FormDataUserUpdate>({
    resolver: zodResolver(UpdateUserSchema),
    defaultValues: {
      gender: "MALE",
    },
  });
  const [isPending, setIsPending] = useState(false);
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: (data: FormDataUserUpdate) => UpdateUserInfoAPI(data),
    onMutate: () => {
      setIsPending(true);
    },
    onSettled: () => setIsPending(false),
    onSuccess: () => {
      navigate("/");
    },
    onError: (error: Error) => {
      setError("root", {
        message: error.message,
      });
    },
  });

  const onSubmit = (formData: FormDataUserUpdate) => {
    mutate(formData);
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
            placeholder="Name"
            StartIcon={<UserIcon className="ml-4 w-8 fill-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("name")}
          />
          <p className="text-purple">{errors.name?.message}</p>

          <p className="text-xl font-bold text-foreground">Gender</p>
          <Controller
            name="gender"
            control={control}
            render={({ field }) => (
              <RadioGroup
                onValueChange={field.onChange}
                value={field.value}
                defaultValue="MALE"
                className="flex flex-col space-y-1 text-base text-foreground "
              >
                <div className="flex items-center space-x-2">
                  <RadioGroupItem
                    value="MALE"
                    id="male"
                    className="bg-foreground text-purple"
                  />
                  <label htmlFor="male" className="flex items-center">
                    Male <MaleIcon className="ml-2 w-6 stroke-foreground" />
                  </label>
                </div>
                <div className="flex items-center space-x-2">
                  <RadioGroupItem
                    value="FEMALE"
                    id="female"
                    className="bg-foreground text-purple"
                  />
                  <label htmlFor="female" className="flex items-center">
                    Female <FemaleIcon className="ml-2 w-6 stroke-foreground" />
                  </label>
                </div>
              </RadioGroup>
            )}
          />
          <p className="text-purple">{errors.gender?.message}</p>
          <Input
            placeholder="Address"
            StartIcon={<AddressIcon className="ml-4 w-8 fill-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("address")}
          />
          <p className="text-purple">{errors.address?.message}</p>

          <Input
            placeholder="Phone Number"
            StartIcon={<PhoneIcon className="ml-4 w-8 stroke-gray" />}
            className="h-14 rounded-3xl pl-14"
            {...register("phoneNumber")}
          />
          <p className="text-purple">{errors.phoneNumber?.message}</p>

          <Button
            type="submit"
            className={`hover: h-14 rounded-3xl bg-purple hover:bg-purple ${
              isPending ? "scale-95 opacity-80" : ""
            }`}
          >
            Create Account
          </Button>
        </div>
      </form>
    </div>
  );
}
