import { APIResponse } from "./APIResponse.type";

export interface Asset {
  id: string;
  name: string;
  imgPath: string;
  description: string;
  price: number;
  highestBid: number;
  authorId: string;
  authorAvatarPath: string;
  authorName: string;
  timestampCreate: string;
  tags: string[];
}

export type AssetCardsResponse = APIResponse & {
  result: Asset[];
};

export type AssetDetailResponse = APIResponse & {
  result: Asset;
};

export type CountAssetsResponse = APIResponse & {
  result: number;
};
