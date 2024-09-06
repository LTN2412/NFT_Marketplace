import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

import HeroSection from "./HeroSection/HeroSection";
import TopCreator from "./TopCreator/TopCreator";
import TrendingCollection from "./TrendingCollection/TrendingCollection";

export default function HomePage() {
  return (
    <div className="pt-[70px] lg:pt-[100px]">
      <Navbar />
      <HeroSection />
      <TrendingCollection />
      <TopCreator />
      <Footer />
    </div>
  );
}
