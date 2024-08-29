import { AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Avatar } from "@radix-ui/react-avatar";
import React, { HTMLAttributes } from "react";
import GlobalIcon from "@/assets/Global.svg?react";
import DiscordIcon from "@/assets/community/DiscordLogo.svg?react";
import YoutubeIcon from "@/assets/community/YoutubeLogo.svg?react";
import TwitterIcon from "@/assets/community/TwitterLogo.svg?react";
import InstagramIcon from "@/assets/community/InstagramLogo.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import { Button } from "@/components/ui/button";
import FirstCharName from "@/utils/FirstCharName";
import LoadingPage from "@/app/LoadingPage/LoadingPage";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { useQuery } from "@tanstack/react-query";
import { GetAuthorDetail } from "@/apis/query-options/AuthorQuery";

export interface AuthorProps extends HTMLAttributes<HTMLDivElement> {
  id: string;
}

const Author = React.forwardRef<HTMLDivElement, AuthorProps>(
  ({ className, id, ...props }, ref) => {
    const { data, isLoading, isError } = useQuery(GetAuthorDetail(id));
    if (isLoading) return <LoadingPage />;
    if (isError) return <ErrorPage />;
    const author = data!.data.result;
    return (
      <div className={cn(className, "bg-background")} ref={ref} {...props}>
        <div className="relative">
          <img className="aspect-[7/3] object-cover" src={"/Cover.png"}></img>
          <div className="h-[60px]"></div>
          <Avatar className="absolute bottom-0 left-[10%] max-sm:left-1/2 max-sm:-translate-x-1/2">
            <AvatarImage
              src={"/Avatar.png"}
              className="aspect-square rounded-xl"
            />
            <AvatarFallback>{FirstCharName(author.name)}</AvatarFallback>
          </Avatar>
        </div>
        <h1 className="gap-6 py-12">
          <p className="text-4xl font-bold max-sm:self-center">{author.name}</p>
          <Button className="flex h-14 w-full items-center gap-2 rounded-xl border-2 border-purple bg-background hover:bg-background hover:opacity-80">
            <PlusIcon className="w-6 fill-purple" />
            Follow
          </Button>
          <div className="flex justify-between text-center">
            <div>
              <div className="text-2xl font-bold">{author.volumne}</div>
              <p className="font-mono text-xl">Volumne</p>
            </div>

            <div>
              <div className="text-2xl font-bold ">{author.nftSolds}</div>
              <p className="font-mono text-xl">NFTs Sold</p>
            </div>

            <div>
              <div className="text-2xl font-bold">{author.followers}</div>
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
        </h1>
      </div>
    );
  },
);

export default Author;
