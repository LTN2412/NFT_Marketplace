import { AuthorDetailResponse, Authors } from "@/types/Author.type";
import { httpElastic } from "@/utils/Http";

export const FetchAuthorDetailAPI = async (authorId: string) => {
  return httpElastic.get<AuthorDetailResponse>("/author", {
    params: {
      authorId: authorId,
    },
  });
};

export const FetchTopAuthorAPI = async (limit: number) => {
  return httpElastic.get<Authors>("/author/top", {
    params: {
      limit: limit,
    },
  });
};
