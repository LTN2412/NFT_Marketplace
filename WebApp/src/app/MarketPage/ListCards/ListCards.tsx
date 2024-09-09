import { useEffect, useRef, useState } from "react";

import { GetAssetsPageable } from "@/apis/query-options/AssetQuery";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { useQueryParams } from "@/hooks/use-query-params";
import { useQuery } from "@tanstack/react-query";

import Card from "@/components/Card/Card";

import MyPagination from "../MyPagination/MyPagination";
import SkeletonListCards from "./SkeletonListCards/SkeletonListCards";

export default function ListCards() {
  const queryParams = useQueryParams();
  const page = Number(queryParams.page) || 1;
  const [limit, setLimit] = useState<number>(12);
  const listRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth <= 640) setLimit(5);
      else if (window.innerWidth <= 1024) setLimit(8);
      else setLimit(12);
    };

    handleResize();
    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  useEffect(() => {
    listRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [page]);

  const { data, isLoading, isError } = useQuery(GetAssetsPageable(page, limit));

  if (isLoading) return <SkeletonListCards limit={limit} />;
  if (isError) return <ErrorPage />;

  const assets = data?.data.result || [];
  const totalElement = data?.data.totalElement ?? 0;
  const totalPage = data?.data.totalPage ?? 1;

  return (
    <div ref={listRef}>
      <div className="grid grid-cols-1 justify-center justify-items-center gap-10 bg-background2 px-10 py-12 md:grid-cols-2 lg:grid-cols-3">
        {assets.map((asset) => (
          <Card
            key={asset.id}
            assetId={asset.id}
            assetName={asset.name}
            assetImg="/test.png"
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
        totalElement={totalElement}
        totalPage={totalPage}
      />
    </div>
  );
}
