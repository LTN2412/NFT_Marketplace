import StoreFrontIcon from "@/assets/Storefront.svg?react";

export default function NavLogo() {
  return (
    <div className="flex items-center gap-3 font-mono text-2xl">
      <StoreFrontIcon className=" w-10 fill-current text-foreground" />
      <p className="whitespace-nowrap">NFT Marketplace</p>
    </div>
  );
}
