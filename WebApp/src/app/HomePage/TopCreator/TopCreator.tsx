import { Button } from "@/components/ui/button";
import RocketLaunchIcon from "@/assets/RocketLaunch.svg?react";
import ArtistGrid from "./ArtistGrid/ArtistGrid";
export default function TopCreator() {
  return (
    <div className="grid h-fit w-full grid-cols-1 items-center gap-10 bg-background px-10 py-[80px] lg:grid-cols-2 lg:px-[100px]">
      <div className="flex flex-col gap-4">
        <b className="whitespace-nowrap text-[32px] lg:text-[43px]">
          Top Creators
        </b>
        <p className="text-[18px]">
          Checkout Top Rated Creators on the NFT Marketplace
        </p>
      </div>
      <ArtistGrid />
      <Button className="text-md flex w-full items-center gap-2 rounded-2xl border-[2px] border-button bg-background p-5 hover:bg-background hover:opacity-80 lg:order-2 lg:w-fit lg:place-self-end lg:py-7 ">
        <RocketLaunchIcon className="w-5 fill-current text-foreground" />
        <p>View Ranking</p>
      </Button>
    </div>
  );
}
