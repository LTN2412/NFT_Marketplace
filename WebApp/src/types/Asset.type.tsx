import { APIResponse } from "./APIResponse.type";

export interface Asset {
  id: string;
  name: string;
  imgPath: string;
  description: string;
  price: number;
  highestBid: number;
  tags: string[];
  authorId: string;
  authorAvatarPath: string;
  authorName: string;
  createdAt: string;
  updatedAt: string;
}

export type AssetsResponse = APIResponse & {
  result: Asset[];
};

export type AssetDetailResponse = APIResponse & {
  result: Asset;
};
