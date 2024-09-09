import { Link } from "react-router-dom";

import ListIcon from "@/assets/List.svg?react";
import UserIcon from "@/assets/User.svg?react";

import { Button } from "@/components/ui/button";

interface NavMenuProps {
  isLogin: boolean;
}

export default function NavMenu({ isLogin }: NavMenuProps) {
  const handleClick = () => {
    const dropdown = document.querySelector("#dropdown");
    dropdown?.classList.toggle("hidden");
  };
  return (
    <div className="flex flex-col lg:order-1 lg:flex-row lg:items-center lg:gap-8 lg:text-xl lg:font-medium">
      <ListIcon
        className="w-8 cursor-pointer stroke-foreground md:w-10 lg:hidden"
        onClick={handleClick}
      />
      <div
        id="dropdown"
        className="absolute inset-x-0 top-[70px] flex hidden flex-col items-center gap-2 border-t-2 border-gray bg-background2 text-center shadow-lg lg:static lg:flex lg:flex-row lg:gap-8 lg:border-none lg:bg-transparent lg:text-2xl lg:shadow-none "
        onClick={handleClick}
      >
        <Link
          to={"/marketplace/nfts"}
          className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold lg:w-auto lg:py-0"
        >
          Marketplace
        </Link>
        <Link
          to={"/ranking"}
          className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold lg:w-auto lg:py-0"
        >
          Ranking
        </Link>
        <Link
          to={"/wallet"}
          className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold lg:w-auto lg:py-0"
        >
          Connect a wallet
        </Link>
        {!isLogin && (
          <Link
            to={"/signup"}
            className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold lg:hidden lg:w-auto lg:py-0"
          >
            Sign Up
          </Link>
        )}
      </div>
      {!isLogin && (
        <Link to={"/signup"} className="hidden lg:block">
          <Button className="hover:bg-purple/80 cursor-pointer gap-2 rounded-xl bg-purple lg:p-6 lg:text-xl">
            <UserIcon className="w-5 fill-current text-foreground lg:w-8" />
            <p>Sign Up</p>
          </Button>
        </Link>
      )}
    </div>
  );
}
