import { useEffect, useRef, useState } from "react";

import { GetAssetsPageable } from "@/apis/query-options/AssetQuery";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { useQueryParams } from "@/hooks/use-query-params";
import { useQuery } from "@tanstack/react-query";

import Card from "@/components/Card/Card";

import MyPagination from "../MyPagination/MyPagination";
import SkeletonListCards from "./SkeletonListCards/SkeletonListCards";

export default function ListCards() {
  const queryPrams: { page?: string } = useQueryParams();
  const page = Number(queryPrams.page) || 1;
  const [limit, setLimit] = useState<number>();
  const listRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (window.innerWidth <= 640) setLimit(5);
    else if (window.innerWidth <= 1024) setLimit(8);
    else setLimit(12);

    if (listRef.current) {
      listRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [page]);

  const { data, isLoading, isError } = useQuery(
    GetAssetsPageable(page, limit!),
  );
  const assets = data?.data.result || Array(limit).fill(0);
  const totalElement = data?.data.totalElement;
  const totalPage = data?.data.totalPage;
  if (isLoading) return <SkeletonListCards limit={assets} />;
  if (isError) return <ErrorPage />;
  return (
    <div ref={listRef}>
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
