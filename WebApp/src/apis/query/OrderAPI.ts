import { APIResponse } from "@/types/APIResponse.type";

import { httpUser } from "@/utils/Http";

export const OrderAPI = async () => {
  return httpUser.post<APIResponse>("/asset/order");
};

export const RemoveAllAssetsIsSelected = async () => {
  return httpUser.delete<APIResponse>("/asset/deleteOrder");
};
