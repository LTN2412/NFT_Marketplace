import { GetAllAssetDetailFrom1Author } from "@/apis/query-options/AssetQuery";

import ListCards from "@/components/ListCards/ListCards";

export default function TrendingCollection() {
  return (
    <div className="h-fit w-full bg-background px-5 py-[80px] md:px-12 lg:px-[100px]">
      <div className="mb-12 flex flex-col gap-4">
        <h2 className="whitespace-nowrap text-4xl font-bold lg:text-[43px]">
          Trending Collection
        </h2>
        <p className="text-xl">
          Checkout Our Weekly Updated Trending Collection
        </p>
      </div>
      <ListCards
        queryOption={GetAllAssetDetailFrom1Author}
        param={"5a3ea269-4bc3-4ff7-a2dd-c53aecf3a415"}
        className="bg-background2"
      />
    </div>
  );
}
