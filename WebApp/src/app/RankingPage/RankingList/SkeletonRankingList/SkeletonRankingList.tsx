import SkeletonCardCreator from "../CardCreator/SkeletonCardCreator/SkeletonCardCreator";

interface SkeletonRankingListProps {
  limit: number;
}

export default function SkeletonRankingList({
  limit,
}: SkeletonRankingListProps) {
  return (
    <h1 className="gap-6">
      <div className="grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl border-2 border-background2 bg-background px-6 py-4 text-gray md:grid-cols-9 lg:grid-cols-11">
        <p className="col-span-1 justify-self-center">#</p>
        <p className="col-span-4">Artists</p>
        <p className="col-span-2 max-md:hidden">Change</p>
        <p className="col-span-2 max-lg:hidden ">NFTs Sold</p>
        <p className="col-span-2">Volumne</p>
      </div>
      <ul className="flex flex-col gap-4">
        {Array(limit)
          .fill(0)
          .map((_, index) => (
            <SkeletonCardCreator key={index} />
          ))}
      </ul>
    </h1>
  );
}
