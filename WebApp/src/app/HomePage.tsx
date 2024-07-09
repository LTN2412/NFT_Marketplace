import Navbar from "@/components/Navbar/Navbar";
import HeroSection from "./HomePage/HeroSection/HeroSection";
import TrendingCollection from "./HomePage/TrendingCollection/TrendingCollection";
import TopCreator from "./HomePage/TopCreator/TopCreator";

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
