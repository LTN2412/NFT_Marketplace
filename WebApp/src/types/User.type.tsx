import { APIResponse } from "./APIResponse.type";

export interface User {
  id: string;
  name: string;
  gender: string;
  email: string;
  address: string;
  phoneNumber: string;
  avatarPath: string;
  createdAt: Date;
  updatedAt: Date;
  lastLogin: Date;
  assetIds: string[];
  friendIds: string[];
  followerIds: string[];
}

// ? Change to form
export interface UserAsset {
  id: string;
  name: string;
  price: number;
  quantity: number;
  imgPath: string;
  isSelect: boolean;
}

export type CartUserResponse = APIResponse & {
  result: UserAsset[];
};

export type UserDetailResponse = APIResponse & {
  result: User[];
};
