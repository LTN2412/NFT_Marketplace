import { APIResponse } from "./APIResponse.type";

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  gender: string;
  email: string;
  address: string;
  phoneNumber: string;
  avatarPath: string;
  createdAt: Date;
  updatedAt: Date;
  lastLogin: Date;
  username: string;
  assetIds: string[];
  friendIds: string[];
  followerIds: string[];
}

export interface UserAsset {
  id: string;
  name: string;
  price: number;
  quantity: number;
  imgPath: string;
}

export type AddUserAsset = UserAsset & {
  userId: string;
};

export type CartUserResponse = APIResponse & {
  result: UserAsset[];
};

export type UserResponse = APIResponse & {
  result: User[];
};
