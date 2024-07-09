import NavLogo from "./NavLogo/NavLogo";
import NavMenu from "./NavMenu/NavMenu";

export default function Navbar() {
  return (
    <div className="flex h-[70px] w-full items-center justify-between bg-background px-8 lg:h-[100px] lg:px-[50px]">
      <NavLogo />
      <NavMenu />
    </div>
  );
}
