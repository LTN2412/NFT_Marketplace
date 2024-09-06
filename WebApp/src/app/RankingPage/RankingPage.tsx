import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

import HeadLine from "./HeadLine/HeadLine";
import TabBar from "./TabBar/TabBar";

export default function RankingPage() {
  return (
    <div className="pt-[70px] lg:pt-[100px]">
      <Navbar />
      <HeadLine />
      <TabBar />
      <Footer />
    </div>
  );
}
