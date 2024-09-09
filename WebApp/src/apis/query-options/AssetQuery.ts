import {
  FetchAllAssetFrom1AuthorAPI,
  FetchAllAssetsByTagAPI,
  FetchAllAssetsInCart,
  FetchAssetDetailAPI,
  FetchAssetsPageableAPI,
  FetchCountAssetsAPI,
} from "@/apis/query/AssetAPI";
import { queryOptions } from "@tanstack/react-query";

export const GetAssetsPageable = (offset: number, limit: number) => {
  return queryOptions({
    queryKey: ["asset_card", offset],
    queryFn: async () => FetchAssetsPageableAPI(offset, limit),
    placeholderData: (prevData) => prevData,
  });
};

export const GetAssetDetail = (assetId: string) => {
  return queryOptions({
    queryKey: ["assets_detail", assetId],
    queryFn: async () => FetchAssetDetailAPI(assetId),
  });
};

export const GetAllAssetDetailFrom1Author = (
  authorId: string,
  limit: number,
) => {
  return queryOptions({
    queryKey: ["assets_from_author", authorId],
    queryFn: async () => FetchAllAssetFrom1AuthorAPI(authorId, limit),
  });
};
export type GetAllAssetDetailFrom1AuthorType =
  typeof GetAllAssetDetailFrom1Author;

export const GetAllAssetsByTag = (nameTag: string, limit: number) => {
  return queryOptions({
    queryKey: ["assets_tag", nameTag],
    queryFn: async () => FetchAllAssetsByTagAPI(nameTag, limit),
  });
};
export type GetAllAssetsByTagType = typeof GetAllAssetsByTag;

export const CountAllAssets = () => {
  return queryOptions({
    queryKey: ["assets_count"],
    queryFn: async () => FetchCountAssetsAPI(),
  });
};

export const GetAllAssetsInCart = (assetIds: string[], enabled: boolean) => {
  return queryOptions({
    queryKey: ["asset_in_cart"],
    queryFn: async () => FetchAllAssetsInCart(assetIds),
    enabled: enabled,
  });
};
