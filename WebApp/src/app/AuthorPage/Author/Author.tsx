import React, { HTMLAttributes, useEffect, useState } from "react";

import { GetAuthorDetail } from "@/apis/query-options/AuthorQuery";
import { CheckFollowStatus } from "@/apis/query-options/UserQuery";
import { AddFollowerAPI, UnFollowerAPI } from "@/apis/query/UserAPI";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import LoadingPage from "@/app/LoadingPage/LoadingPage";
import GlobalIcon from "@/assets/Global.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import DiscordIcon from "@/assets/community/DiscordLogo.svg?react";
import InstagramIcon from "@/assets/community/InstagramLogo.svg?react";
import TwitterIcon from "@/assets/community/TwitterLogo.svg?react";
import YoutubeIcon from "@/assets/community/YoutubeLogo.svg?react";
import { useToast } from "@/hooks/use-toast";
import { cn } from "@/lib/utils";
import { Avatar } from "@radix-ui/react-avatar";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import { AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";

import FirstCharName from "@/utils/FirstCharName";

export interface AuthorProps extends HTMLAttributes<HTMLDivElement> {
  authorId: string;
}

const Author = React.forwardRef<HTMLDivElement, AuthorProps>(
  ({ className, authorId, ...props }, ref) => {
    const queryClient = useQueryClient();
    const { toast } = useToast();
    const [numberFollowers, setNumberFollowers] = useState<number>(0);
    const [isFollow, setIsFollow] = useState<boolean>(false);

    // *Query author detail
    const {
      data: authorDetail,
      isLoading: isAuthorLoading,
      isError: isAuthorError,
    } = useQuery(GetAuthorDetail(authorId));

    // *Query follow status
    const {
      data: checkFollower,
      isLoading: isFollowerLoading,
      isError: isFollowerError,
    } = useQuery(CheckFollowStatus(authorId));

    useEffect(() => {
      if (authorDetail?.data.result)
        setNumberFollowers(authorDetail.data.result.followers);
      if (checkFollower?.data.result) setIsFollow(checkFollower.data.result);
    }, [authorDetail, checkFollower]);

    // *Mutation follow
    const handleFollowMutation = useMutation({
      mutationFn: (isFollowing: boolean) =>
        isFollowing ? UnFollowerAPI(authorId) : AddFollowerAPI(authorId),
      onMutate: (isFollowing) => {
        setIsFollow(!isFollowing);
        setNumberFollowers((prev) => (isFollowing ? prev - 1 : prev + 1));
      },
      onError: (_, isFollowing) => {
        setIsFollow(isFollowing);
        setNumberFollowers((prev) => (isFollowing ? prev + 1 : prev - 1));
        toast({
          title: "Error",
          description: "Oops! Something went wrong!",
        });
      },
      onSuccess: () => {
        setIsFollow(true);
        setTimeout(() => {
          queryClient.invalidateQueries({ queryKey: ["author", authorId] });
        }, 1000);
      },
    });

    // *Mutation unfollow
    const handleUnFollowMutation = useMutation({
      mutationFn: (isFollowing: boolean) =>
        isFollowing ? UnFollowerAPI(authorId) : AddFollowerAPI(authorId),
      onMutate: (isFollowing) => {
        setIsFollow(!isFollowing);
        setNumberFollowers((prev) => (isFollowing ? prev - 1 : prev + 1));
      },
      onError: (_, isFollowing) => {
        setIsFollow(isFollowing);
        setNumberFollowers((prev) => (isFollowing ? prev + 1 : prev - 1));
        toast({
          title: "Error",
          description: "Oops! Something went wrong!",
        });
      },
      onSuccess: () => {
        setIsFollow(false);
        setTimeout(() => {
          queryClient.invalidateQueries({ queryKey: ["author", authorId] });
        }, 1000);
      },
    });

    if (isAuthorLoading || isFollowerLoading) return <LoadingPage />;
    if (isAuthorError || isFollowerError) return <ErrorPage />;

    const author = authorDetail?.data.result;

    if (!author) return null;

    return (
      <div className={cn(className, "bg-background")} ref={ref} {...props}>
        <div className="relative">
          <img
            className="h-[300px] w-full object-cover"
            src={"/Cover.png"}
            alt="Cover"
          />
          <div className="h-[60px]"></div>
          <Avatar className="absolute bottom-0 left-[10%] max-sm:left-1/2 max-sm:-translate-x-1/2">
            <AvatarImage
              src={"/Avatar.png"}
              className="aspect-square rounded-xl"
              alt={author.name}
            />
            <AvatarFallback>{FirstCharName(author.name)}</AvatarFallback>
          </Avatar>
        </div>
        <div className="flex flex-col gap-6 px-5 py-12 md:px-12 lg:px-24">
          <p className="text-4xl font-bold max-sm:self-center">{author.name}</p>
          {!isFollow ? (
            <Button
              className="flex h-14 w-full items-center gap-2 rounded-xl border-2 border-purple bg-background hover:bg-background hover:opacity-80"
              onClick={() => handleFollowMutation.mutate(isFollow)}
            >
              Follow
              <PlusIcon className="w-6 fill-purple" />
            </Button>
          ) : (
            <Button
              className="flex h-14 w-full items-center gap-2 rounded-xl border-2 border-purple bg-background hover:bg-background hover:opacity-80"
              onClick={() => handleUnFollowMutation.mutate(isFollow)}
            >
              UnFollow
              <PlusIcon className="w-6 fill-purple" />
            </Button>
          )}
          <div className="flex justify-between text-center">
            <div>
              <div className="text-2xl font-bold">{author.volumne}</div>
              <p className="font-mono text-xl">Volume</p>
            </div>
            <div>
              <div className="text-2xl font-bold ">{author.nftSolds}</div>
              <p className="font-mono text-xl">NFTs Sold</p>
            </div>
            <div>
              <div className="text-2xl font-bold">{numberFollowers}</div>
              <p className="font-mono text-xl">Followers</p>
            </div>
          </div>
          <div>
            <p className="font-mono text-lg text-gray">Bio</p>
            <p>{author.bio}</p>
          </div>
          <div>
            <p className="font-mono text-lg text-gray">Link</p>
            <div className="flex justify-start gap-4">
              <GlobalIcon className="w-10 cursor-pointer fill-gray" />
              <DiscordIcon className="w-10 cursor-pointer fill-gray" />
              <YoutubeIcon className="w-10 cursor-pointer fill-gray" />
              <TwitterIcon className="w-10 cursor-pointer fill-gray" />
              <InstagramIcon className="w-10 cursor-pointer fill-gray" />
            </div>
          </div>
        </div>
      </div>
    );
  },
);

export default Author;
