import { useState } from "react";
import React from "react";

import { GetAllCommentFrom1AssetId } from "@/apis/query-options/CommentQuery";
import { CreateCommentAPI } from "@/apis/query/CommentAPI";
import StarIcon from "@/assets/Star.svg?react";
import { useToast } from "@/hooks/use-toast";
import { FormDataComment } from "@/types/schema/CommentSchema";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

import Comment from "./Comment/Comment";

export type ListCommentProps = {
  assetId: string;
  isLogin: boolean;
};

const ListComment = React.forwardRef<HTMLDivElement, ListCommentProps>(
  ({ assetId, isLogin, ...props }, ref) => {
    const queryClient = useQueryClient();
    const { toast } = useToast();
    const [formComment, setFormComment] = useState<FormDataComment>({
      assetId,
      userComment: "",
      rating: "0",
    });
    const { data } = useQuery(GetAllCommentFrom1AssetId(assetId));
    const { mutate } = useMutation({
      mutationFn: (formData: FormDataComment) => {
        return CreateCommentAPI(formData);
      },
      onSuccess: () => {
        toast({
          description: "You have send your comment.",
        });
        queryClient.invalidateQueries({ queryKey: ["comment", assetId] });
      },
    });
    const comments = data?.data.result || [];
    const handleSubmit = (event: React.FormEvent) => {
      event.preventDefault();
      if (formComment.userComment && formComment.rating !== "0") {
        mutate(formComment);
      } else {
        toast({
          title: "Error",
          description: "Please enter a comment and select a star rating.",
          variant: "destructive",
        });
      }
    };

    return (
      <div className="flex w-full flex-col gap-4" ref={ref} {...props}>
        {comments.length === 0 ? (
          <p>There is no comment yet!</p>
        ) : (
          comments.map((comment) => (
            <Comment
              key={comment.id}
              id={comment.id}
              username={comment.username}
              userAvatarPath={comment.userAvatarPath}
              rating={comment.rating}
              userComment={comment.userComment}
              updatedAt={comment.updatedAt}
            />
          ))
        )}
        {isLogin && (
          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <Input
              className="bg-background"
              placeholder="Leave your comment here"
              value={formComment.userComment}
              onChange={(e) =>
                setFormComment({ ...formComment, userComment: e.target.value })
              }
            />
            <div className="flex gap-1">
              {Array(5)
                .fill(0)
                .map((_, index) => (
                  <StarIcon
                    key={index}
                    className={`h-6 w-6 cursor-pointer ${index < parseInt(formComment.rating) ? "fill-yellow-500" : "stroke-yellow-500"}`}
                    onClick={() =>
                      setFormComment({
                        ...formComment,
                        rating: (index + 1).toString(),
                      })
                    }
                  />
                ))}
            </div>
            <div className="flex w-full justify-end">
              <Button
                type="submit"
                className="w-full rounded-2xl bg-purple py-6 text-xl hover:bg-purple md:w-44 md:px-8"
              >
                Send
              </Button>
            </div>
          </form>
        )}
      </div>
    );
  },
);

export default ListComment;
