import { queryOptions } from "@tanstack/react-query";
import { FetchAuthorDetailAPI, FetchTopAuthorAPI } from "../query/AuthorAPI";

export const GetAuthorDetail = (id: string) => {
  return queryOptions({
    queryKey: ["author", id],
    queryFn: async () => FetchAuthorDetailAPI(id),
  });
};

export const GetTopAuthors = (limit: number) => {
  return queryOptions({
    queryKey: ["top_authors", limit],
    queryFn: async () => FetchTopAuthorAPI(limit),
  });
};
