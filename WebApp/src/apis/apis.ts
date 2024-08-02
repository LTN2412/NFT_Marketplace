import { AssetDetailResponse, AssetCardsResponse } from "@/types/Asset.type";
import { AuthorDetailResponse } from "@/types/Author.type";
import http from "@/utils/Http";

export const FetchAssetsAPI = async (offset: number, limit: number) => {
  return http.get<AssetCardsResponse>("/asset/page", {
    params: {
      offset: offset - 1,
      limit: limit,
    },
  });
};

export const FetchAssetDetail = async (id: string) => {
  return http
    .get<AssetDetailResponse>("/asset", {
      params: {
        id: id,
      },
    })
    .catch((err) => {
      throw new Error(err);
    });
};

export const FetchAllAssetFrom1Author = async (id: string, limit: number) => {
  return http.get<AssetCardsResponse>("/asset/fromAuthor", {
    params: {
      id: id,
      limit: limit,
    },
  });
};

export const FetchAllAssetsByTag = async (name: string, limit: number) => {
  return http.get<AssetCardsResponse>("/asset/byTag", {
    params: {
      name: name,
      limit: limit,
    },
  });
};

export const FetchAuthorDetail = async (id: string) => {
  return http.get<AuthorDetailResponse>("/author", {
    params: {
      id: id,
    },
  });
};

export const FetchSearchAssets = async (query: string, limit: number) => {
  return http.get<AssetCardsResponse>("/asset/search", {
    params: {
      field: "name",
      query: query,
      limit: limit,
    },
  });
};
