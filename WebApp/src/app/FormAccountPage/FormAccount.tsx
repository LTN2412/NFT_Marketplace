import { Outlet } from "react-router-dom";
import HeadLine from "./HeadLine/HeadLine";
import Navbar from "@/components/Navbar/Navbar";
import Footer from "@/components/Footer/Footer";

export default function FormAccountPage() {
  return (
    <div>
      <Navbar />
      <div className="flex w-full border-b bg-background ">
        <div className="">
          <img className="object-cover" src={"/ImageAccount.png"}></img>
        </div>
        <div className=" w-1/2 pl-24">
          <HeadLine />
          <Outlet />
        </div>
      </div>
      <Footer />
    </div>
  );
}
