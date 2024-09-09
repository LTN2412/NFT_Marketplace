import Cookies from "js-cookie";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import FirstCharName from "@/utils/FirstCharName";

export default function AvatarMenu() {
  const handleLogout = () => {
    Cookies.remove("accessToken");
    window.location.reload();
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger className="order-1 lg:order-3">
        <Avatar className="h-10 w-10 md:h-12 md:w-12 lg:h-14 lg:w-14">
          <AvatarImage src={"/Avatar.png"} className="rounded-xl" />
          <AvatarFallback>{FirstCharName("Abc")}</AvatarFallback>
        </Avatar>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="bg-background text-foreground">
        <DropdownMenuLabel>My Account</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuItem
          onClick={handleLogout}
          className="cursor-pointer bg-background focus:bg-background focus:text-foreground "
        >
          Logout
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
