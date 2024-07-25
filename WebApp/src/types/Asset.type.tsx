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

export type AssetCardsResponse = {
  code: number;
  totalElement: number;
  totalPage: number;
  result: Asset[];
};

export type AssetDetailResponse = {
  code: number;
  result: Asset;
};
