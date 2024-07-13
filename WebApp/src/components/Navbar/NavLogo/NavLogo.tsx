import StoreFrontIcon from "@/assets/Storefront.svg?react";
import { Link } from "react-router-dom";

export default function NavLogo() {
  return (
    <Link to={"/"} className="flex items-center gap-3 font-mono text-2xl">
      <StoreFrontIcon className=" fill-purple w-10" />
      <p className="whitespace-nowrap">NFT Marketplace</p>
    </Link>
  );
}
