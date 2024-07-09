import { Button } from "@/components/ui/button";
import RocketLaunchIcon from "@/assets/RocketLaunch.svg?react";
import HighlightNFT from "./HighlightNFT/HighlightNFT";

export default function HeroSection() {
  return (
    <div className="grid grid-cols-1 place-items-center gap-10 bg-background px-10 py-12 sm:grid-cols-2 lg:px-[100px]">
      <div className="flex flex-col gap-4 lg:gap-6">
        <b className="text-[43px] lg:text-[67px] ">
          Discover Digital Art & Collect NFTs
        </b>
        <p className="text-lg lg:text-xl">
          NFT Marketplace UI. Collect, Buy and Sell Art From More Than 20k NFT
          Artists.
        </p>
      </div>
      <HighlightNFT />
      <Button className=" w-full gap-2 rounded-2xl bg-button px-11 py-7 text-[18px] hover:bg-button hover:opacity-80 lg:w-fit lg:place-self-start">
        <RocketLaunchIcon className="w-5 fill-current text-foreground " />
        <p className="font-medium">Get Started</p>
      </Button>
      <div className="flex w-full items-center justify-between whitespace-nowrap px-5 text-xl lg:text-3xl">
        <div>
          <b className="font-bold">240k+</b>
          <p>Total Sale</p>
        </div>
        <div>
          <b className="font-bold">100k+</b>
          <p>Auctions</p>
        </div>
        <div>
          <b>240k+</b>
          <p>Artists</p>
        </div>
      </div>
    </div>
  );
}
