import { Avatar } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import FirstCharName from "@/utils/FirstCharName";
import { AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";
import React from "react";
import { Link } from "react-router-dom";

export interface CardCreatorProps extends React.HTMLAttributes<HTMLDivElement> {
  number: number;
  authorName: string;
  volumne: number;
  change: number;
  sold: number;
}

const CardCreator = React.forwardRef<HTMLDivElement, CardCreatorProps>(
  ({ className, number, authorName, volumne, change, sold, ...props }, ref) => {
    return (
      <div
        className={cn(
          "grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl bg-background2 px-6 py-4 md:grid-cols-9 lg:grid-cols-11",
          className,
        )}
        ref={ref}
        {...props}
      >
        <p className="justify-self-center text-gray">{number}</p>
        <Link to={"/author"}>
          <Avatar className="">
            <AvatarImage src="https://github.com/shadcn.png"></AvatarImage>
            <AvatarFallback>{FirstCharName(authorName)}</AvatarFallback>
          </Avatar>
        </Link>
        <p className="col-span-3 whitespace-nowrap font-semibold">
          {authorName}
        </p>
        <p
          className={`col-span-2 max-md:hidden ${change == 0 ? "" : change > 0 ? "text-green-500" : "text-red-600"}`}
        >
          {change} %
        </p>
        <p className="col-span-2 max-lg:hidden">{sold}</p>
        <p className="col-span-2 font-mono">{volumne} ETH</p>
      </div>
    );
  },
);

export default CardCreator;
