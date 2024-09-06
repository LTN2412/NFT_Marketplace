import { useEffect, useState } from "react";
import NavLogo from "./NavLogo/NavLogo";
import NavMenu from "./NavMenu/NavMenu";
import BellMessage from "./BellMessage/BellMessage";
import Cookies from "js-cookie";

export default function Navbar() {
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    Cookies.get("accessToken") ? setIsLogin(true) : null;
  }, []);
  return (
    <div className="fixed top-0 z-50 flex h-[70px] w-full items-center justify-between bg-background px-5 text-center md:px-12 lg:h-[100px] lg:px-24">
      <NavLogo />
      <div className="flex items-center justify-center gap-4">
        {isLogin && <BellMessage />}
        <NavMenu isLogin={isLogin} />
      </div>
    </div>
  );
}
