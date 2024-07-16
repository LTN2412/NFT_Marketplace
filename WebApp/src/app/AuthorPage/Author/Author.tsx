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

export interface AuthorProps extends HTMLAttributes<HTMLDivElement> {
  authorCoverImg?: string;
  authorAvatar: string;
  authorName: string;
  authorVolumne: number;
  authorSold: number;
  authorFollower: number;
  authorBio: string;
}

const Author = React.forwardRef<HTMLDivElement, AuthorProps>(
  (
    {
      className,
      authorCoverImg,
      authorAvatar,
      authorName,
      authorVolumne,
      authorSold,
      authorFollower,
      authorBio,
      ...props
    },
    ref,
  ) => {
    return (
      <div className={cn(className, "bg-background")} ref={ref} {...props}>
        <div className="relative">
          <img className="aspect-[7/3] object-cover" src={authorCoverImg}></img>
          <div className="h-[60px]"></div>
          <Avatar className="absolute bottom-0 left-[10%] max-sm:left-1/2 max-sm:-translate-x-1/2">
            <AvatarImage
              src={authorAvatar}
              className="aspect-square rounded-xl"
            />
            <AvatarFallback>{FirstCharName(authorName)}</AvatarFallback>
          </Avatar>
        </div>
        <div className="flex flex-col gap-6 px-8 py-12">
          <p className="text-4xl font-bold">{authorName}</p>
          <Button className="flex h-14 w-full items-center gap-2 rounded-xl border-2 border-purple bg-background hover:bg-background hover:opacity-80">
            <PlusIcon className="w-6 fill-purple" />
            Follow
          </Button>
          <div className="flex justify-between ">
            <div>
              <div className="text-2xl font-bold">{authorVolumne}</div>
              <p className="font-mono text-xl">Volumne</p>
            </div>

            <div>
              <div className="text-2xl font-bold ">{authorSold}</div>
              <p className="font-mono text-xl">NFTs Sold</p>
            </div>

            <div>
              <div className="text-2xl font-bold">{authorFollower}</div>
              <p className="font-mono text-xl">Followers</p>
            </div>
          </div>
          <div>
            <p className="font-mono text-lg text-gray">Bio</p>
            <p>{authorBio}</p>
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
