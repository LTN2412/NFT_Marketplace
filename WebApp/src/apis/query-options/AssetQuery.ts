import {
  FetchAssetsPageableAPI,
  FetchAssetDetailAPI,
  FetchAllAssetFrom1AuthorAPI,
  FetchAllAssetsByTagAPI,
  FetchCountAssets,
} from "@/apis/query/AssetAPI";
import { queryOptions } from "@tanstack/react-query";

export const GetAssetsPageableAPI = (offset: number, limit: number) => {
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

export const GetAllAssetDetailFrom1Author = (id: string, limit: number) => {
  return queryOptions({
    queryKey: ["assets_from_author", id],
    queryFn: async () => FetchAllAssetFrom1AuthorAPI(id, limit),
  });
};
export type GetAllAssetDetailFrom1AuthorType =
  typeof GetAllAssetDetailFrom1Author;

export const GetAllAssetsByTag = (name: string, limit: number) => {
  return queryOptions({
    queryKey: ["assets_tag", name],
    queryFn: async () => FetchAllAssetsByTagAPI(name, limit),
  });
};
export type GetAllAssetsByTagType = typeof GetAllAssetsByTag;

export const CountAllAssets = () => {
  return queryOptions({
    queryKey: ["assets_count"],
    queryFn: async () => FetchCountAssets(),
  });
};
