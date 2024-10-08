import { Link } from "react-router-dom";

import EmailIcon from "@/assets/Email.svg?react";
import StoreFrontIcon from "@/assets/Storefront.svg?react";

import { Button } from "../ui/button";
import { Input } from "../ui/input";
import Community from "./Community/Community";

export default function Footer() {
  return (
    <div className="bg-background px-5 py-12 md:px-12 lg:px-24">
      <div className="flex flex-col gap-2 lg:flex-row lg:justify-between">
        <div className="flex flex-col gap-2">
          <div className="flex items-center gap-3 font-mono text-2xl">
            <StoreFrontIcon className=" w-10 fill-current text-purple" />
            <p className="whitespace-nowrap font-semibold">NFT Marketplace</p>
          </div>
          <p className="text-lightGray">NFT Marketplace for everyone </p>
          <p className="text-lightGray">Join our community</p>
          <Community></Community>
        </div>

        <div>
          <p className="text-2xl font-bold">Explore</p>
          <div className="mt-4 flex flex-col gap-3">
            <Link to={"/marketplace"} className="text-lightGray">
              Marketplace
            </Link>
            <Link to={"/ranking"} className="text-lightGray">
              Ranking
            </Link>
            <Link to={"/wallet"} className="text-lightGray">
              Connect a wallet
            </Link>
          </div>
        </div>

        <div className="flex flex-col gap-4 lg:pb-12">
          <p className="text-2xl font-bold">Join Our Weekly Digest</p>
          <p className="text-lightGray">
            Get exclusive promotions & updates straight to your inbox.
          </p>
          <div className="flex flex-col gap-4 md:flex-row md:gap-0 lg:relative">
            <Input
              type="email"
              placeholder="Enter your email here"
              className="h-12 w-full rounded-2xl border bg-foreground text-black md:w-[300px] md:pr-20 md:focus-visible:ring-0"
            />
            <Button
              type="submit"
              className="flex h-12 w-full items-center justify-center gap-1 rounded-2xl bg-purple hover:scale-100 hover:bg-purple hover:opacity-100 md:absolute md:left-[280px] md:w-[140px] lg:left-[260px]"
            >
              <EmailIcon className="w-7 fill-foreground lg:hidden" />
              Subscribe
            </Button>
          </div>
          <p className="border-t border-gray pt-4 text-lightGray lg:hidden">
            © NFT Marketplace
          </p>
        </div>
      </div>
      <div className="w-full border-t border-gray bg-background py-6 text-lightGray max-lg:hidden max-lg:px-24">
        © NFT Marketplace LTN
      </div>
    </div>
  );
}
