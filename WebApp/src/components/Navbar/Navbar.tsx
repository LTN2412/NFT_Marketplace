import { useEffect, useState } from "react";

import Cookies from "js-cookie";

import AvatarMenu from "./AvatarMenu/AvatarMenu";
import BellMessage from "./BellMessage/BellMessage";
import NavLogo from "./NavLogo/NavLogo";
import NavMenu from "./NavMenu/NavMenu";

export default function Navbar() {
  const [isLogin, setIsLogin] = useState<boolean>(false);

  useEffect(() => {
    const checkLoginStatus = () => {
      const accessToken = Cookies.get("accessToken");
      setIsLogin(!!accessToken);
    };
    checkLoginStatus();
  }, []);

  return (
    <div className="fixed top-0 z-50 flex h-[70px] w-full items-center justify-between bg-background px-5 text-center md:px-12 lg:h-[100px] lg:px-24">
      <NavLogo />
      <div className="flex items-center justify-center gap-4">
        {isLogin && (
          <>
            <BellMessage />
            <AvatarMenu />
          </>
        )}
        <NavMenu isLogin={isLogin} />
      </div>
    </div>
  );
}
