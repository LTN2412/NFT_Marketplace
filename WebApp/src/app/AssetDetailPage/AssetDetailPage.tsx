import AssetDetail from "./AssetDetail/AssetDetail";
import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function AssetDetailPage() {
  const location = useLocation().pathname;
  const id = location.split("/").pop() || "";
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  return (
    <div>
      <Navbar />
      <AssetDetail assetId={id} />
      <Footer />
    </div>
  );
}
