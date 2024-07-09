import CollectionCard from "./CollectionCard/CollectionCard";

export default function TrendingCollection() {
  return (
    <div className="h-fit w-full bg-background px-10 py-[80px] lg:px-[100px] ">
      <div className="mb-12 flex flex-col gap-4">
        <b className="whitespace-nowrap text-[32px] lg:text-[43px]">
          Trending Collection
        </b>
        <p className="text-[18px]">
          Checkout Our Weekly Updated Trending Collection
        </p>
      </div>
      <CollectionCard />
    </div>
  );
}
