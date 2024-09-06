import React, { useEffect, useState } from "react";
import { HTMLAttributes } from "react";

import {
  GetAllAssetDetailFrom1AuthorType,
  GetAllAssetsByTagType,
} from "@/apis/query-options/AssetQuery";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import SkeletonListCards from "@/app/MarketPage/ListCards/SkeletonListCards/SkeletonListCards";
import { cn } from "@/lib/utils";
import { Asset } from "@/types/Asset.type";
import { useQuery } from "@tanstack/react-query";

import Card from "../Card/Card";

export interface ListCardsProps extends HTMLAttributes<HTMLDivElement> {
  queryOption: GetAllAssetDetailFrom1AuthorType | GetAllAssetsByTagType;
  param: string;
  cardType?: string;
}

const ListCards = React.forwardRef<HTMLDivElement, ListCardsProps>(
  ({ className, queryOption, param, cardType, ...props }, ref) => {
    const [limit, setLimit] = useState<number>();
    useEffect(() => {
      if (window.innerWidth <= 640) setLimit(3);
      else if (window.innerWidth <= 1024) setLimit(6);
      else setLimit(9);
    }, []);
    const { data, isLoading, isError } = useQuery(queryOption(param, limit!));

    if (isLoading) return <SkeletonListCards limit={Array(limit).fill(0)} />;
    if (isError) return <ErrorPage />;

    const assets: Asset[] = data!.data.result || Array(limit).fill(0);
    return (
      <div
        className={cn(
          `grid grid-cols-1 justify-items-center gap-10 px-10 py-12 md:grid-cols-2 lg:grid-cols-3`,
          className,
        )}
        ref={ref}
        {...props}
      >
        {assets.map((asset) => (
          <Card
            assetId={asset.id}
            assetName={asset.name}
            assetImg={"/test.png"}
            assetPrice={asset.price}
            assetHighestBid={asset.highestBid}
            authorId={asset.authorId}
            authorName={asset.authorName}
            authorAvatar={asset.authorAvatarPath}
            className={`${cardType}`}
          />
        ))}
      </div>
    );
  },
);

export default ListCards;
