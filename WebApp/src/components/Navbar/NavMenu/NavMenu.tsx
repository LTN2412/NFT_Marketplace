import { Button } from "@/components/ui/button";
import ListIcon from "@/assets/List.svg?react";
import UserIcon from "@/assets/User.svg?react";

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
          <ListIcon className="w-8 bg-background fill-current text-foreground" />
        </Button>
        <div
          id="dropdown"
          className=" absolute inset-x-0 top-[70px] flex hidden flex-col items-center gap-2 whitespace-nowrap bg-background2 text-center text-lg shadow-lg"
        >
          <b className="w-full cursor-pointer py-4 hover:bg-gray-500 ">
            Marketplace
          </b>
          <b className="w-full cursor-pointer py-4 hover:bg-gray-500">
            Rankings
          </b>
          <b className="hover:bg-gry-500 w-full cursor-pointer py-4">
            Connect a wallet
          </b>
        </div>
      </div>
      {/* Laptop */}
      <div className="hidden items-center gap-8 text-xl font-medium lg:flex">
        <p className="cursor-pointer">Marketplace</p>
        <p className="cursor-pointer">Rankings</p>
        <p className="cursor-pointer">Connect a wallet</p>
        <Button className="cursor-pointer gap-2 rounded-xl bg-button hover:bg-button hover:opacity-80">
          <UserIcon className="w-5 fill-current text-foreground" />
          <p>Sign Up</p>
        </Button>
      </div>
    </div>
  );
}
