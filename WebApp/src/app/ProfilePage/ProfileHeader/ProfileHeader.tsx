import GlobalIcon from "@/assets/Global.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import DiscordIcon from "@/assets/community/DiscordLogo.svg?react";
import InstagramIcon from "@/assets/community/InstagramLogo.svg?react";
import TwitterIcon from "@/assets/community/TwitterLogo.svg?react";
import YoutubeIcon from "@/assets/community/YoutubeLogo.svg?react";
import { cn } from "@/lib/utils";
import { Avatar, AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";

import { Button } from "@/components/ui/button";

import FirstCharName from "@/utils/FirstCharName";

export default function ProfileHeader() {
  return (
    <div>
      <div className={cn("bg-background")}>
        <div className="relative">
          <img
            className="h-[300px] w-full object-cover"
            src={"/Cover.png"}
          ></img>
          <div className="h-[60px]"></div>
          <Avatar className="absolute bottom-0 left-[10%] max-sm:left-1/2 max-sm:-translate-x-1/2">
            <AvatarImage
              src={"/Avatar.png"}
              className="aspect-square rounded-xl"
            />
            <AvatarFallback>{FirstCharName("test")}</AvatarFallback>
          </Avatar>
        </div>
        <h1 className="gap-6 py-12">
          <p className="text-4xl font-bold max-sm:self-center">{"test"}</p>
          <Button className="flex h-14 w-full items-center gap-2 rounded-xl border-2 border-purple bg-background hover:bg-background hover:opacity-80">
            <PlusIcon className="w-6 fill-purple" />
            Follow
          </Button>
          <div className="flex justify-between text-center">
            <p className="font-mono text-lg text-gray">Bio</p>
            <p>{"asd"}</p>
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
          <p>FriendIds</p>
        </h1>
      </div>
    </div>
  );
}
