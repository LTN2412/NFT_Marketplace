import AssetDetail from "./AssetDetail/AssetDetail";
import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

const mockData = {
  assetSrc: "/2.png",
  assetName: "The Orbitians",
  mintedAt: "Sep 30 2024",
  authorAvatar: "https://github.com/shadcn.png",
  authorName: "Le Thanh Nhat",
  description: `
  The Orbitians is a collection of 10,000 unique NFTs on the Ethereum
        blockchain,There are all sorts of beings in the NFT Universe. The most
        advanced and friendly of the bunch are Orbitians. They live in a metal
        space machines, high up in the sky and only have one foot on Earth.
        These Orbitians are a peaceful race, but they have been at war with a
        group of invaders for many generations. The invaders are called
        Upside-Downs, because of their inverted bodies that live on the ground,
        yet do not know any other way to be. Upside-Downs believe that they will
        be able to win this war if they could only get an eye into Orbitian
        territory, so they've taken to make human beings their target.
  `,
  tags: ["Animation", "Illustration", "Moon"],
};

export default function AssetDetailPage() {
  return (
    <div>
      <Navbar />
      <AssetDetail
        assetSrc={mockData.assetSrc}
        assetName={mockData.assetName}
        mintedAt={mockData.mintedAt}
        authorAvatar={mockData.authorAvatar}
        authorName={mockData.authorName}
        description={mockData.description}
        tags={mockData.tags}
      />
      <Footer />
    </div>
  );
}
