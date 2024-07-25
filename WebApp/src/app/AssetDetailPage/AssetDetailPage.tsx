import { FetchAssetDetail } from "@/apis/apis";
import AssetDetail from "./AssetDetail/AssetDetail";
import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import { useQuery } from "@tanstack/react-query";
import formatDate from "@/utils/FormatDateTime";
import { useLocation } from "react-router-dom";

export default function AssetDetailPage() {
  const location = useLocation().pathname;
  const id = location.split("/").pop() || "";
  const { data: dataAssetId } = useQuery({
    queryKey: ["asset_id", id],
    queryFn: async () => FetchAssetDetail(id),
  });
  const { data: dataAssetCard } = useQuery({
    queryKey: ["asset_card"],
    queryFn: async () => FetchAssetsAPI(page, limit),
    placeholderData: (prevData) => prevData,
  });
  if (dataAssetId?.data == undefined) {
    return <h1>ERROR</h1>;
  }
  const asset = dataAssetId.data.result;

  return (
    <div>
      <Navbar />
      <AssetDetail
        assetImg={"/2.png"}
        assetName={asset.name}
        description={asset.description}
        mintedAt={formatDate(asset.timestampCreate)}
        authorAvatar={asset.authorAvatarPath}
        authorName={asset.authorName}
        tags={asset.tags}
      />
      <Footer />
    </div>
  );
}
