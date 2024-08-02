import AssetDetail from "./AssetDetail/AssetDetail";
import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import { useLocation } from "react-router-dom";

export default function AssetDetailPage() {
  const location = useLocation().pathname;
  const id = location.split("/").pop() || "";
  return (
    <div>
      <Navbar />
      <AssetDetail id={id} />
      <Footer />
    </div>
  );
}
