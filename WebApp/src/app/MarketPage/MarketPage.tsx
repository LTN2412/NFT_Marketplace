import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import HeadLine from "./HeadLine/HeadLine";
import TabBar from "./TabBar/TabBar";
import CartCircle from "@/components/CartCircle/CartCircle";
import { useEffect, useState } from "react";
import Cookies from "js-cookie";
// import { useEffect } from "react";
// import { fetchAssets } from "@/redux/AssetSlice";
// import { useAppDispatch } from "@/store";

export default function MarketPage() {
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    Cookies.get("accessToken") ? setIsLogin(true) : null;
  }, []);
  return (
    <div className="pt-[70px] lg:pt-[100px]">
      <Navbar />
      <HeadLine />
      <TabBar />
      {isLogin && <CartCircle />}
      <Footer />
    </div>
  );
}
