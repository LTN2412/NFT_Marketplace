import { APIResponse } from "@/types/APIResponse.type";
import {
  AddUserAsset,
  CartUserResponse,
  UserResponse,
} from "@/types/User.type";
import { httpUser } from "@/utils/Http";

export const FetchUserAPI = async (userId: string) => {
  return httpUser.get<UserResponse>("/author", {
    params: {
      userId: userId,
    },
  });
};

export const FetchCartUserAPI = async (userId: string) => {
  return httpUser.get<CartUserResponse>("/asset", {
    params: {
      userId: userId,
    },
  });
};

export const AddCartItemAPI = async (addUserAsset: AddUserAsset) => {
  return httpUser.post<AddUserAsset>("/asset", addUserAsset);
};

export const DeleteCartItemAPI = async (userId: string, assetId: string) => {
  return httpUser.delete<APIResponse>("/asset", {
    params: {
      userId: userId,
      assetId: assetId,
    },
  });
};

export const AcceptFriendAPI = async (
  messageId: string,
  userRequestId: string,
  userReceiveId: string,
) => {
  return httpUser.post<APIResponse>("/acceptFriend", null, {
    params: {
      messageId: messageId,
      userRequestId: userRequestId,
      userReceiveId: userReceiveId,
    },
  });
};

export const RejectFriendAPI = async (
  messageId: string,
  userRequestId: string,
  userReceiveId: string,
) => {
  return httpUser.post<APIResponse>("/rejectFriend", null, {
    params: {
      messageId: messageId,
      userRequestId: userRequestId,
      userReceiveId: userReceiveId,
    },
  });
};
