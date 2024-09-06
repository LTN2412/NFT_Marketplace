import { queryOptions } from "@tanstack/react-query";

import { FetchAllCommentsFrom1AssetIdAPI } from "../query/CommentAPI";

export const GetAllCommentFrom1AssetId = (assetId: string) => {
  return queryOptions({
    queryKey: ["comment", assetId],
    queryFn: async () => FetchAllCommentsFrom1AssetIdAPI(assetId),
  });
};
