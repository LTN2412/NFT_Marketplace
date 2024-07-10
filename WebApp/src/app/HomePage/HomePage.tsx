import Navbar from "@/components/Navbar/Navbar";
import HeroSection from "./HeroSection/HeroSection";
import TrendingCollection from "./TrendingCollection/TrendingCollection";
import TopCreator from "./TopCreator/TopCreator";

export default function HomePage() {
  return (
    <div>
      <Navbar />
      <HeroSection />
      <TrendingCollection />
      <TopCreator />
    </div>
  );
}
