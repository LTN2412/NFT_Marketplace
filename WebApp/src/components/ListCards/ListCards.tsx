import { cn } from "@/lib/utils";
import React from "react";
import { HTMLAttributes } from "react";
import Card from "../Card/Card";

export interface ListCardsProps extends HTMLAttributes<HTMLDivElement> {}

const arr = [1, 2, 3, 4];

const ListCards = React.forwardRef<HTMLDivElement, ListCardsProps>(
  ({ className, ...props }, ref) => {
    return (
      <div
        className={cn(
          "grid grid-cols-1 justify-items-center gap-10 bg-background px-8 py-12 md:grid-cols-2 lg:grid-cols-3",
          className,
        )}
        ref={ref}
        {...props}
      >
        {arr.map(() => (
          <Card
            className="bg-background2"
            assetSrc={"/test.png"}
            assetName={"Galaxy"}
            assetPrice={"100"}
            assetHighestBid={"200"}
            authorSrc={"asd"}
            authorName={"LTN"}
          />
        ))}
      </div>
    );
  },
);

export default ListCards;
