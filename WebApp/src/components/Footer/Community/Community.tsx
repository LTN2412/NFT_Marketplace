import DiscordLogo from "@/assets/community/DiscordLogo.svg?react";
import YoutubeLogo from "@/assets/community/YoutubeLogo.svg?react";
import TwitterLogo from "@/assets/community/TwitterLogo.svg?react";
import InstagramLogo from "@/assets/community/InstagramLogo.svg?react";

export default function Community() {
  return (
    <div>
      <div className="flex gap-2">
        <DiscordLogo className="fill-gray w-10" />
        <YoutubeLogo className="fill-gray w-10" />
        <TwitterLogo className="stroke-gray w-10 fill-none" />
        <InstagramLogo className="fill-gray w-10" />
      </div>
    </div>
  );
}
