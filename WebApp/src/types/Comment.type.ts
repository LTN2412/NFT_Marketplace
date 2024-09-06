import { APIResponse } from "./APIResponse.type";

export interface Comment {
  id: string;
  username: string;
  userAvatarPath: string;
  rating: number;
  userComment: string;
  createdAt: string;
  updatedAt: string;
  assetId: string;
}

export type CommentDetailResponse = APIResponse & {
  result: Comment;
};

export type CommentsResponse = APIResponse & {
  result: Comment[];
};
