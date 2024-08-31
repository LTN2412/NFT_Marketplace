import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import HeadLine from "./HeadLine/HeadLine";
import TabBar from "./TabBar/TabBar";
import CartCircle from "@/components/CartCircle/CartCircle";
// import { useEffect } from "react";
// import { fetchAssets } from "@/redux/AssetSlice";
// import { useAppDispatch } from "@/store";

export default function MarketPage() {
  // const dispatch = useAppDispatch();
  // useEffect(() => {
  //   dispatch(fetchAssets());
  // }, [dispatch]);
  return (
    <div>
      <CartCircle />
      <Navbar />
      <HeadLine />
      <TabBar />
      <Footer />
    </div>
  );
}
