import Card from "@/components/Card/Card";
// import { useAppSelector } from "@/store";
import { useQueryParams } from "@/utils/Hook";
import { useQuery } from "@tanstack/react-query";
import MyPagination from "../MyPagination/MyPagination";
import SkeletonListCards from "./SkeletonListCards/SkeletonListCards";

import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { GetAssetsPageableAPI } from "@/apis/query-options/AssetQuery";

export default function ListCards() {
  // const objectAsset = useAppSelector((state) => state.asset.entities);
  // const assets = Object.values(objectAsset);
  const queryPrams: { page?: string } = useQueryParams();
  const page = Number(queryPrams.page) || 1;
  const limit = 15;
  const { data, isLoading, isError } = useQuery(
    GetAssetsPageableAPI(page, limit),
  );
  const assets = data?.data.result || Array(limit).fill(0);
  const totalElement = data?.data.totalElement;
  const totalPage = data?.data.totalPage;
  if (isLoading) return <SkeletonListCards limit={assets} />;
  if (isError) return <ErrorPage />;
  return (
    <div>
      <div className="grid grid-cols-1 justify-center justify-items-center gap-10 bg-background2 px-10 py-12 md:grid-cols-2 lg:grid-cols-3">
        {assets.map((asset) => (
          <Card
            key={asset.id}
            assetId={asset.id}
            assetName={asset.name}
            assetImg={"/test.png"}
            assetPrice={asset.price}
            assetHighestBid={asset.highestBid}
            authorId={asset.authorId}
            authorName={asset.authorName}
            authorAvatar={asset.authorAvatarPath}
          />
        ))}
      </div>
      <MyPagination
        currentPage={page}
        totalElement={totalElement!}
        totalPage={totalPage!}
      />
    </div>
  );
}
