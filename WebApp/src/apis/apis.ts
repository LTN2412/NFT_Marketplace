import { AssetDetailResponse, AssetCardsResponse } from "@/types/Asset.type";
import http from "@/utils/Http";

export const FetchAssetsAPI = async (page: number, limit: number) => {
  return http.get<AssetCardsResponse>("/asset/card", {
    params: {
      page: page,
      limit: limit,
    },
  });
};

export const FetchAssetDetail = async (id: string) => {
  return http.get<AssetDetailResponse>("/asset", {
    params: {
      id: id,
    },
  });
};
