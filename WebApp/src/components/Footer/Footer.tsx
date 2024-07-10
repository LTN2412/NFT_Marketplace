import StoreFrontIcon from "@/assets/Storefront.svg?react";
import Community from "./Community/Community";
export default function Footer() {
  return (
    <div className="h-48 w-full bg-slate-600">
      <div className="flex items-center gap-3 font-mono text-2xl">
        <StoreFrontIcon className=" w-10 fill-current text-button" />
        <p className="whitespace-nowrap font-semibold">NFT Marketplace</p>
      </div>
      <p>NFT Marketplace for everyone </p>
      <Community></Community>
    </div>
  );
}
