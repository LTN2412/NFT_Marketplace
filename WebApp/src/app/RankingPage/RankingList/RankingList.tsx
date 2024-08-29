import { GetTopAuthors } from "@/apis/query-options/AuthorQuery";
import CardCreator from "./CardCreator/CardCreator";
import SkeletonRankingList from "./SkeletonRankingList/SkeletonRankingList";
import { useQuery } from "@tanstack/react-query";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { random } from "lodash";

export default function RankingList() {
  const limit = 5;
  const { data, isLoading, isError } = useQuery(GetTopAuthors(limit));
  const authors = data?.data.result;
  if (isLoading) return <SkeletonRankingList limit={limit} />;
  if (isError) return <ErrorPage />;
  return (
    <h1 className="gap-6">
      <div className="grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl border-2 border-background2 bg-background px-6 py-4 text-gray md:grid-cols-9 lg:grid-cols-11">
        <p className="col-span-1 justify-self-center">#</p>
        <p className="col-span-4">Artists</p>
        <p className="col-span-2 max-md:hidden">Change</p>
        <p className="col-span-2 max-lg:hidden">NFTs Sold</p>
        <p className="col-span-2">Volumne</p>
      </div>
      {authors?.map((author, index) => (
        <CardCreator
          number={index + 1}
          id={author.id}
          avatarPath={"https://github.com/shadcn.png"}
          name={author.name}
          volumne={author.volumne}
          change={random(-10, 10)}
          sold={author.nftSolds}
        />
      ))}
    </h1>
  );
}
