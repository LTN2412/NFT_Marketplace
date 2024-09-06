import { CountResponse } from "@/types/APIResponse.type";
import { AssetCardsResponse, AssetDetailResponse } from "@/types/Asset.type";

import { httpElastic } from "@/utils/Http";

export const FetchAssetsPageableAPI = async (offset: number, limit: number) => {
  return httpElastic.get<AssetCardsResponse>("/asset/page", {
    params: {
      offset: offset - 1,
      limit: limit,
    },
  });
};

export const FetchAssetDetailAPI = async (assetId: string) => {
  return httpElastic.get<AssetDetailResponse>("/asset", {
    params: {
      assetId: assetId,
    },
  });
};

export const FetchAllAssetFrom1AuthorAPI = async (
  authorId: string,
  limit: number,
) => {
  return httpElastic.get<AssetCardsResponse>("/asset/fromAuthor", {
    params: {
      authorId: authorId,
      limit: limit,
    },
  });
};

export const FetchAllAssetsByTagAPI = async (
  tagName: string,
  limit: number,
) => {
  return httpElastic.get<AssetCardsResponse>("/asset/byTag", {
    params: {
      tagName: tagName,
      limit: limit,
    },
  });
};

export const FetchSearchAssetsAPI = async (query: string, limit: number) => {
  return httpElastic.get<AssetCardsResponse>("/asset/search", {
    params: {
      field: "name",
      query: query,
      limit: limit,
    },
  });
};

export const FetchCountAssetsAPI = async () => {
  return httpElastic.get<CountResponse>("/asset/count");
};
