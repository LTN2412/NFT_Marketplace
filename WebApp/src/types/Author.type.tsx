export interface Author {
  id: string;
  name: string;
  avatarPath: string;
  coverImgPath: string;
  bio: string;
  volumne: number;
  nftSolds: number;
  followers: number;
  assetIds: string[];
}

export type AuthorDetailResponse = {
  code: number;
  result: Author;
};
