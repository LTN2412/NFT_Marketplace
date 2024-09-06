import { useEffect, useState } from "react";

import { GetAllAssetDetailFrom1Author } from "@/apis/query-options/AssetQuery";

import ListCards from "@/components/ListCards/ListCards";

export default function TrendingCollection() {
  const mockAuthorId = "";
  const [cardLimit, setCardLimit] = useState(3);
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 1024) {
        setCardLimit(6);
      } else if (window.innerWidth >= 768) {
        setCardLimit(4);
      } else {
        setCardLimit(3);
      }
    };

    handleResize(); // Initial check
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <div className="h-fit w-full bg-background px-5 py-[80px] md:px-12 lg:px-[100px]">
      <div className="mb-12 flex flex-col gap-4">
        <b className="whitespace-nowrap text-4xl lg:text-[43px]">
          Trending Collection
        </b>
        <p className="text-xl">
          Checkout Our Weekly Updated Trending Collection
        </p>
      </div>
      <ListCards
        queryOption={GetAllAssetDetailFrom1Author}
        param={mockAuthorId}
        limit={cardLimit}
      />
    </div>
  );
}
