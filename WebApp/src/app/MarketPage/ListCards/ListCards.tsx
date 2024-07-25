import Card from "@/components/Card/Card";
// import { useAppSelector } from "@/store";
import { useQueryParams } from "@/utils/Hook";
import { useQuery } from "@tanstack/react-query";
import { FetchAssetsAPI } from "@/apis/apis";
import MyPagination from "../MyPagination/MyPagination";
import SkeletonListCards from "./SkeletonListCards/SkeletonListCards";

export default function ListCards() {
  // const objectAsset = useAppSelector((state) => state.asset.entities);
  // const assets = Object.values(objectAsset);
  const queryPrams: { page?: string } = useQueryParams();
  const page = Number(queryPrams.page) || 1;
  const limit = 15;
  const { data, isLoading, isError } = useQuery({
    queryKey: ["asset_card", page],
    queryFn: async () => FetchAssetsAPI(page, limit),
    placeholderData: (prevData) => prevData,
  });
  const assets = data?.data.result || Array(limit).fill(0);
  const totalElement = data?.data.totalElement;
  const totalPage = data?.data.totalPage;

  if (isLoading) return <SkeletonListCards limit={assets} />;
  return (
    <>
      {isError ? (
        <h1>Error</h1>
      ) : (
        <div>
          <div className="grid grid-cols-1 justify-center justify-items-center gap-10 bg-background2 px-10 py-12 md:grid-cols-2 lg:grid-cols-3">
            {assets.map((asset) => (
              <Card
                assetId={asset.id}
                assetName={asset.name}
                assetImg={"/test.png"}
                assetPrice={String(asset.price)}
                assetHighestBid={String(asset.highestBid)}
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
      )}
    </>
  );
}
