import StoreFrontIcon from "@/assets/Storefront.svg?react";
import Community from "./Community/Community";
import { Link } from "react-router-dom";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import EmailIcon from "@/assets/Email.svg?react";
export default function Footer() {
  return (
    <div className="flex h-full w-full flex-col gap-2 border-t-2 border-gray bg-background2 px-8 py-12">
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
          <Link to={"/rankings"} className="text-lightGray">
            Rankings
          </Link>
          <Link to={"/wallet"} className="text-lightGray">
            Connect a wallet
          </Link>
        </div>
      </div>

      <div className="flex flex-col gap-4">
        <p className="text-2xl font-bold">Join Our Weekly Digest</p>
        <p className="text-lightGray">
          Get exclusive promotions & updates straight to your inbox.
        </p>
        <div className="flex flex-col gap-4 lg:flex-row lg:gap-0">
          <Input
            type="email"
            placeholder="Enter your email here"
            className="h-12 w-full rounded-2xl bg-foreground text-black lg:w-[200px] lg:rounded-r-none lg:focus:outline-none"
          />
          <Button
            type="submit"
            className="flex h-12 w-full items-center justify-center gap-1 rounded-2xl bg-purple hover:bg-purple hover:opacity-80 focus:outline-none lg:absolute lg:left-[250px] lg:w-[150px]"
          >
            <EmailIcon className="w-7 fill-foreground lg:hidden" />
            Subscribe
          </Button>
        </div>
        <p className="border-t border-gray pt-4 text-lightGray">
          © NFT Marketplace
        </p>
      </div>
    </div>
  );
}
