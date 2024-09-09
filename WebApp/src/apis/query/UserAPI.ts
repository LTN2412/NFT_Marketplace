import {
  APIResponse,
  BooleanResponse,
  IdsResponse,
} from "@/types/APIResponse.type";
import {
  CartUserResponse,
  UserAsset,
  UserDetailResponse,
} from "@/types/User.type";
import { FormDataUserUpdate } from "@/types/schema/UpdateUserSchema";

import { httpUser } from "@/utils/Http";

export const UpdateUserInfoAPI = async (data: FormDataUserUpdate) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
  }
  return httpUser.put<UserDetailResponse>("", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const FetchUserAPI = async () => {
  return httpUser.get<UserDetailResponse>("");
};

export const FetchCartUserAPI = async () => {
  return httpUser.get<CartUserResponse>("/asset");
};

export const AddCartItemAPI = async (addUserAsset: UserAsset) => {
  return httpUser.post<APIResponse>("/asset", addUserAsset);
};

export const DeleteCartItemAPI = async (assetId: string) => {
  return httpUser.delete<APIResponse>("/asset", {
    params: {
      assetId: assetId,
    },
  });
};

export const UpdateIsSelectItemAPI = async (
  assetId: string,
  isSelect: boolean,
) => {
  return httpUser.post<APIResponse>("/asset/select", null, {
    params: {
      assetId: assetId,
      isSelect: isSelect,
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

export const FetchAllFriendsAPI = async () => {
  return httpUser.get<IdsResponse>("/friends");
};

export const CheckFollowStatusAPI = async (userId: string) => {
  return httpUser.get<BooleanResponse>("/checkFollower", {
    params: {
      userId: userId,
    },
  });
};

export const AddFollowerAPI = async (userId: string) => {
  return httpUser.put<APIResponse>("/addFollower", null, {
    params: {
      userReceiveId: userId,
    },
  });
};

export const UnFollowerAPI = async (userId: string) => {
  return httpUser.delete<APIResponse>("/unFollower", {
    params: {
      userReceiveId: userId,
    },
  });
};
