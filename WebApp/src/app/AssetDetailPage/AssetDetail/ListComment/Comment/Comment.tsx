import React from "react";

import StarIcon from "@/assets/Star.svg?react";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

import FirstCharName from "@/utils/FirstCharName";
import FormatTimeAgo from "@/utils/FormatTimeAgo";

interface CommentProps extends React.HTMLAttributes<HTMLDivElement> {
  id: string;
  username: string;
  userAvatarPath: string;
  rating: number;
  userComment: string;
  updatedAt: string;
}

const Comment = React.forwardRef<HTMLDivElement, CommentProps>(
  ({ username, rating, userComment, updatedAt, ...props }, ref) => {
    return (
      <div
        className="flex w-fit flex-col gap-4 rounded-3xl bg-background2 px-12 py-4"
        ref={ref}
        {...props}
      >
        <div className="flex items-center gap-4">
          <Avatar>
            <AvatarImage
              src={"https://github.com/shadcn.png"}
              className="w-10 rounded-3xl"
            />
            <AvatarFallback className="w-10 content-center bg-blue-500 text-center text-foreground">
              {FirstCharName(username)}
            </AvatarFallback>
          </Avatar>
          <p>{username}</p>
        </div>
        <div className="flex items-center gap-1">
          {Array(5)
            .fill(0)
            .map((_, index) => (
              <StarIcon
                key={index}
                className={`h-4 w-4 ${index < rating ? "fill-yellow-500" : "stroke-yellow-500"}`}
              />
            ))}
        </div>
        <p>{userComment}</p>
        <div className="flex justify-end">
          <p className="text-base text-gray">{FormatTimeAgo(updatedAt)}</p>
        </div>
      </div>
    );
  },
);

export default Comment;
