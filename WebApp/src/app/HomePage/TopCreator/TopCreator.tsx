import { Button } from "@/components/ui/button";
import RocketLaunchIcon from "@/assets/RocketLaunch.svg?react";
import ArtistGrid from "./ArtistGrid/ArtistGrid";
import { Link } from "react-router-dom";

export default function TopCreator() {
  return (
    <div className="grid h-fit w-full  items-center gap-10 bg-background px-5 py-[80px] md:px-12 lg:grid-cols-2 lg:px-24">
      <div className="flex flex-col gap-4">
        <b className="whitespace-nowrap text-4xl lg:text-[43px]">
          Top Creators
        </b>
        <p className="text-xl">
          Checkout Top Rated Creators on the NFT Marketplace
        </p>
      </div>
      <ArtistGrid />
      <Link to={"/ranking"} className="justify-self-end">
        <Button className="text-md w-full items-center gap-2 rounded-2xl bg-purple py-7 text-xl hover:bg-purple hover:opacity-80 max-lg:flex lg:order-2 lg:w-fit lg:py-7">
          <RocketLaunchIcon className="w-6 fill-current text-foreground" />
          <p>View Ranking</p>
        </Button>
      </Link>
    </div>
  );
}
