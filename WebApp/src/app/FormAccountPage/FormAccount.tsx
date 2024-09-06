import { Outlet } from "react-router-dom";

import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

export default function FormAccountPage() {
  return (
    <div className="pt-[70px] lg:pt-[100px]">
      <Navbar />
      <div className="flex w-full flex-col border-b bg-background md:flex-row">
        <div>
          <img className="object-contain" src={"/ImageAccount.png"}></img>
        </div>
        <div className="flex flex-col max-sm:items-center">
          <Outlet />
        </div>
      </div>
      <Footer />
    </div>
  );
}
