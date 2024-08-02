import {
  FetchAllAssetFrom1Author,
  FetchAllAssetsByTag,
  FetchAssetDetail,
  FetchAssetsAPI,
  FetchAuthorDetail,
} from "@/apis/apis";
import { queryOptions } from "@tanstack/react-query";

export const GetAssetsAPI = (offset: number, limit: number) => {
  return queryOptions({
    queryKey: ["asset_card", offset],
    queryFn: async () => FetchAssetsAPI(offset, limit),
    placeholderData: (prevData) => prevData,
    refetchOnWindowFocus: false,
  });
};

export const GetAssetDetail = (id: string) => {
  return queryOptions({
    queryKey: ["assets_from_author", id],
    queryFn: async () => FetchAssetDetail(id),
  });
};

export const GetAllAssetDetailFrom1Author = (id: string, limit: number) => {
  return queryOptions({
    queryKey: ["assets_from_author", id],
    queryFn: async () => FetchAllAssetFrom1Author(id, limit),
  });
};

export type GetAllAssetDetailFrom1AuthorType =
  typeof GetAllAssetDetailFrom1Author;

export const GetAllAssetsByTag = (name: string, limit: number) => {
  return queryOptions({
    queryKey: ["assets_tag", name],
    queryFn: async () => FetchAllAssetsByTag(name, limit),
  });
};

export type GetAllAssetsByTagType = typeof GetAllAssetsByTag;

export const GetAuthorDetail = (id: string) => {
  return queryOptions({
    queryKey: ["author", id],
    queryFn: async () => FetchAuthorDetail(id),
  });
};
