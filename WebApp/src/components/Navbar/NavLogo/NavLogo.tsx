import StoreFrontIcon from "@/assets/Storefront.svg?react";
import { Link } from "react-router-dom";

export default function NavLogo() {
  return (
    <Link to={"/"} className="flex items-center gap-3 font-mono">
      <StoreFrontIcon className="w-10 fill-purple md:w-12 lg:w-14" />
      <p className="whitespace-nowrap text-2xl md:text-3xl lg:text-4xl">
        NFT Marketplace
      </p>
    </Link>
  );
}
