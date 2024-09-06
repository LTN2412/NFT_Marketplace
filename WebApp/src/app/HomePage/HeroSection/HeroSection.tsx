import { Button } from "@/components/ui/button";
import RocketLaunchIcon from "@/assets/RocketLaunch.svg?react";
import HighlightNFT from "./HighlightNFT/HighlightNFT";

export default function HeroSection() {
  return (
    <div className="grid grid-cols-1 place-items-center gap-10 bg-background px-5 py-12 sm:grid-cols-2 md:gap-6 md:px-12 lg:px-24">
      <div className="flex flex-col gap-10 lg:gap-6">
        <p className="text-5xl font-bold md:text-6xl lg:text-7xl  ">
          Discover Digital Art & Collect NFTs
        </p>
        <p className="text-xl lg:text-xl">
          NFT Marketplace UI. Collect, Buy and Sell Art From More Than 20k NFT
          Artists.
        </p>
      </div>
      <HighlightNFT />
      <Button className="w-3/4 gap-2 rounded-2xl bg-purple p-7 text-xl hover:bg-purple lg:w-fit lg:place-self-start">
        <RocketLaunchIcon className="w-6 fill-current text-foreground" />
        <p>Get Started</p>
      </Button>
      <div className="flex w-full items-center justify-between whitespace-nowrap px-5 text-xl lg:text-3xl">
        <div>
          <p className="font-bold">240k+</p>
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
