import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import Headline from "./Headline/HeadLine";
import TabBar from "./TabBar/TabBar";

export default function MarketPage() {
  return (
    <div>
      <Navbar />
      <Headline />
      <TabBar />
      <Footer />
    </div>
  );
}
