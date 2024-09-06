import { CommentDetailResponse, CommentsResponse } from "@/types/Comment.type";
import { FormDataComment } from "@/types/schema/CommentSchema";

import { httpUser } from "@/utils/Http";

export const FetchAllCommentsFrom1AssetIdAPI = async (assetId: string) => {
  return httpUser.get<CommentsResponse>("/comment/allFromAsset", {
    params: {
      assetId: assetId,
    },
  });
};

export const CreateCommentAPI = async (data: FormDataComment) => {
  const formData = new FormData();
  for (const [key, value] of Object.entries(data)) {
    formData.append(key, value);
  }
  return httpUser.post<CommentDetailResponse>("/comment", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
