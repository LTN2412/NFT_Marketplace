import NavLogo from "./NavLogo/NavLogo";
import NavMenu from "./NavMenu/NavMenu";
import BellMessage from "./BellMessage/BellMessage";

export default function Navbar() {
  return (
    <div className="flex h-[70px] w-full items-center justify-between bg-background px-10 text-center md:px-16 lg:h-[100px] lg:px-24">
      <NavLogo />
      <NavMenu />
      <BellMessage />
    </div>
  );
}
