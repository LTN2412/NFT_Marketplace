import React from "react";
import { Link } from "react-router-dom";

import { cn } from "@/lib/utils";
import { AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";

import { Avatar } from "@/components/ui/avatar";

import FirstCharName from "@/utils/FirstCharName";

export interface CardCreatorProps extends React.HTMLAttributes<HTMLDivElement> {
  number: number;
  id: string;
  avatarPath: string;
  name: string;
  volumne: number;
  change: number;
  sold: number;
}

const CardCreator = React.forwardRef<HTMLDivElement, CardCreatorProps>(
  (
    {
      className,
      number,
      id,
      avatarPath,
      name,
      volumne,
      change,
      sold,
      ...props
    },
    ref,
  ) => {
    return (
      <div
        className={cn(
          "grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl bg-background2 px-6 py-4 md:grid-cols-9 lg:grid-cols-11",
          className,
        )}
        ref={ref}
        {...props}
      >
        <p
          className={`justify-self-center text-lg ${number == 1 ? "text-[#E4C69A]" : number == 2 ? "text-[#E0E0E0]" : number == 3 ? "text-[#CD7F32]" : "text-gray"}`}
        >
          {number}
        </p>
        <Link to={`/author/${id}`}>
          <Avatar className="h-12 w-12 rounded-full">
            <AvatarImage src={avatarPath}></AvatarImage>
            <AvatarFallback>{FirstCharName(name)}</AvatarFallback>
          </Avatar>
        </Link>
        <p className="col-span-3 whitespace-nowrap font-semibold">{name}</p>
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
