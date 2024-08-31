import { APIResponse } from "@/types/APIResponse.type";
import {
  AddUserAsset,
  CartUserResponse,
  UserResponse,
} from "@/types/User.type";
import { httpUser } from "@/utils/Http";

export const FetchUserAPI = async () => {
  return httpUser.get<UserResponse>("");
};

export const FetchCartUserAPI = async () => {
  return httpUser.get<CartUserResponse>("/asset");
};

export const AddCartItemAPI = async (addUserAsset: AddUserAsset) => {
  return httpUser.post<AddUserAsset>("/asset", addUserAsset);
};

export const DeleteCartItemAPI = async (assetId: string) => {
  return httpUser.delete<APIResponse>("/asset", {
    params: {
      assetId: assetId,
    },
  });
};

export const AcceptFriendAPI = async (
  messageId: string,
  userReceiveId: string,
) => {
  return httpUser.put<APIResponse>("/acceptFriend", null, {
    params: {
      messageId: messageId,
      userReceiveId: userReceiveId,
    },
  });
};

export const RejectFriendAPI = async (
  messageId: string,
  userReceiveId: string,
) => {
  return httpUser.put<APIResponse>("/rejectFriend", null, {
    params: {
      messageId: messageId,
      userReceiveId: userReceiveId,
    },
  });
};
