import { Button } from "@/components/ui/button";
import ListIcon from "@/assets/List.svg?react";
import UserIcon from "@/assets/User.svg?react";
import { Link } from "react-router-dom";

export default function NavMenu() {
  const handleClick = () => {
    const dropdown = document.querySelector("#dropdown");
    dropdown?.classList.toggle("hidden");
  };
  return (
    <div>
      {/* Mobile */}
      <div className="lg:hidden">
        <Button
          className="bg-background hover:bg-background"
          onClick={handleClick}
        >
          <ListIcon className="w-8 fill-foreground" />
        </Button>
        <div
          id="dropdown"
          className=" absolute inset-x-0 top-[70px] flex hidden flex-col items-center gap-2 whitespace-nowrap border-t-2 border-gray bg-background2 text-center text-lg shadow-lg"
          onClick={handleClick}
        >
          <Link
            to={"/marketplace"}
            className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold"
          >
            Marketplace
          </Link>
          <Link
            to={"/rankings"}
            className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold"
          >
            Rankings
          </Link>
          <Link
            to={"/wallet"}
            className="hover:bg-gray-500 w-full cursor-pointer py-4 font-bold"
          >
            Connect a wallet
          </Link>
        </div>
      </div>
      {/* Laptop */}
      <div className="hidden items-center gap-8 text-xl font-medium lg:flex">
        <Link to={"/marketplace"} className="cursor-pointer">
          Marketplace
        </Link>
        <Link to={"/rankings"} className="cursor-pointer">
          Rankings
        </Link>
        <Link to={"/wallet"} className="cursor-pointer">
          Connect a wallet
        </Link>
        <Button className="cursor-pointer gap-2 rounded-xl bg-button hover:bg-button hover:opacity-80">
          <UserIcon className="w-5 fill-current text-foreground" />
          <p>Sign Up</p>
        </Button>
      </div>
    </div>
  );
}
