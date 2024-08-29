import { APIResponse } from "./APIResponse.type";

export enum MessageType {
  ADD_FRIEND = "ADD_FRIEND",
  ACCEPT_FRIEND = "ACCEPT_FRIEND",
  REJECT_FRIEND = "REJECT_FRIEND",
  ADD_FOLLOWER = "ADD_FOLLOWER",
  UNFRIEND = "UNFRIEND",
  UNFOLLOWER = "UNFOLLOWER",
  ADD_COMMENT = "ADD_COMMENT",
}

export interface Message {
  id: string;
  userRequestId: string;
  userRequestName: string;
  userRequestAvatarPath: string;
  userReceiveId: string;
  createdAt: string;
  updatedAt: string;
  friendStatus: string;
  messageType: string;
  isSeen: boolean;
}

export type CountMessagesResponse = APIResponse & {
  result: number;
};

export type MessagesResponse = APIResponse & {
  result: Message[];
};
