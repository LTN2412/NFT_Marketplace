import { UpdateIsSeenMessageAPI } from "@/apis/query/NotificationAPI";
import { AcceptFriendAPI, RejectFriendAPI } from "@/apis/query/UserAPI";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Message, MessageType } from "@/types/Message.type";
import FirstCharName from "@/utils/FirstCharName";
import FormatTimeAgo from "@/utils/FormatTimeAgo";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { HTMLAttributes } from "react";
import { Link } from "react-router-dom";

export interface MessageCardProps extends HTMLAttributes<HTMLLIElement> {
  message: Message;
}
export default function MessageCard({ message }: MessageCardProps) {
  const queryClient = useQueryClient();
  // * Mutation accept friend
  const { mutate: mutateAccept } = useMutation({
    mutationFn: () => {
      return AcceptFriendAPI(message.id, message.userReceiveId);
    },
    onMutate: () => {
      message.messageType = MessageType.ACCEPT_FRIEND;
      message.updatedAt = FormatTimeAgo(new Date().toISOString());
      queryClient.invalidateQueries({ queryKey: ["notification_not_seen"] });
    },
  });

  // * Mutation reject friend
  const { mutate: mutateReject } = useMutation({
    mutationFn: () => {
      return RejectFriendAPI(message.id, message.userReceiveId);
    },
    onMutate: () => {
      message.messageType = MessageType.ACCEPT_FRIEND;
      message.updatedAt = FormatTimeAgo(new Date().toISOString());
      queryClient.invalidateQueries({ queryKey: ["notification_not_seen"] });
    },
  });

  // * Mutation update Is Seen
  const { mutate: mutateIsSeen } = useMutation({
    mutationFn: () => {
      return UpdateIsSeenMessageAPI(message.id);
    },
    onSuccess: () => {
      message.isSeen = true;
      queryClient.invalidateQueries({ queryKey: ["notification_not_seen"] });
    },
  });

  const handleAcceptFriend = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>,
  ) => {
    event.preventDefault();
    mutateAccept();
  };

  const handleRejectFriend = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>,
  ) => {
    event.preventDefault();
    mutateReject();
  };

  const handleSeenMessage = () => {
    mutateIsSeen();
  };

  const ContentMessage = () => {
    switch (message.messageType) {
      case MessageType.ADD_FRIEND:
        return (
          <div className="flex flex-col">
            <p className="overflow-hidden text-ellipsis pb-2">
              <b>{message.userRequestName}</b> send you a friend request request
            </p>
            <div className="flex gap-3">
              <Button
                onClick={handleAcceptFriend}
                className="h-10 w-24 bg-purple hover:bg-purple"
              >
                Accept
              </Button>
              <Button
                onClick={handleRejectFriend}
                className="h-10 w-24 bg-purple hover:bg-purple"
              >
                Reject
              </Button>
            </div>
          </div>
        );
      case MessageType.ACCEPT_FRIEND:
        return (
          <p className="h-full overflow-hidden text-ellipsis">
            You have accepted friend with <b>{message.userRequestName}</b>
          </p>
        );
      case MessageType.REJECT_FRIEND:
        return (
          <p className="h-full overflow-hidden text-ellipsis">
            You have rejected to be friend with <b>{message.userRequestName}</b>
          </p>
        );
      case MessageType.ADD_FOLLOWER:
        return (
          <p className="h-full overflow-hidden text-ellipsis">
            <b>{message.userRequestName}</b> started following you
          </p>
        );
      default:
        break;
    }
  };
  return (
    <Link
      to={`/author/${message.userRequestId}`}
      onClick={handleSeenMessage}
      className={`flex w-fit items-center gap-4 border-x-2 border-b-2 p-6 first:rounded-t-3xl first:border-t-2 last:rounded-b-3xl last:border-b-2 ${!message.isSeen ? "bg-background2" : "bg-background1"}`}
    >
      <Avatar className="h-14 w-14">
        <AvatarImage src={"/Avatar.png"} />
        <AvatarFallback className="w-10 content-center bg-red-900 text-center text-foreground">
          {FirstCharName(message.userRequestName)}
        </AvatarFallback>
      </Avatar>
      <div className="flex h-24 w-96 flex-col text-left first:h-full">
        <ContentMessage />
        <p className="self-end justify-self-end text-gray">
          {FormatTimeAgo(message.updatedAt)}
        </p>
      </div>
    </Link>
  );
}
