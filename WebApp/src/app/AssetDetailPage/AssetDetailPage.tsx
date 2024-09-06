import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

import Cookies from "js-cookie";

import CartCircle from "@/components/CartCircle/CartCircle";
import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

import AssetDetail from "./AssetDetail/AssetDetail";

export default function AssetDetailPage() {
  const location = useLocation().pathname;
  const assetId = location.split("/").pop() || "";
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    Cookies.get("accessToken") ? setIsLogin(true) : null;
    window.scrollTo(0, 0);
  }, [assetId]);

  return (
    <div className="pt-[70px] lg:pt-[100px]">
      <Navbar />
      <AssetDetail assetId={assetId} isLogin={isLogin} />
      {isLogin && <CartCircle />}
      <Footer />
    </div>
  );
}
