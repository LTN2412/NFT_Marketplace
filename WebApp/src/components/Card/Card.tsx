import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import FirstCharName from "@/utils/FirstCharName";
import { AvatarImage } from "@radix-ui/react-avatar";
import React from "react";
import { Link } from "react-router-dom";

export interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
  assetId: string;
  assetImg: string;
  assetName: string;
  assetPrice: string;
  assetHighestBid: string;
  authorId: string;
  authorAvatar: string;
  authorName: string;
}

const Card = React.forwardRef<HTMLDivElement, CardProps>(
  (
    {
      className,
      assetId,
      assetImg,
      assetName,
      assetPrice,
      assetHighestBid,
      authorId,
      authorAvatar,
      authorName,
      ...props
    },
    ref,
  ) => {
    return (
      <Link to={`/asset/${assetId}`}>
        <div
          className={cn("w-[320px] rounded-3xl bg-background", className)}
          ref={ref}
          {...props}
        >
          <div className="w-full rounded-t-3xl">
            <img
              src={assetImg}
              className="aspect-[320/285] w-full object-cover"
            ></img>
          </div>

          <div className="flex flex-col gap-5 p-5">
            <p className="text-2xl font-bold">{assetName}</p>

            <Link
              to={`/author/${authorId}`}
              className="flex items-center gap-5"
            >
              <Avatar>
                <AvatarImage src={authorAvatar} className="w-56 rounded-full" />
                <AvatarFallback className="text-black">
                  {FirstCharName(authorName)}
                </AvatarFallback>
              </Avatar>
              <p className="font-mono">{authorName}</p>
            </Link>

            <div className="font-mono">
              <div className="flex justify-between">
                <div className="text-left">
                  <p className=" text-gray">Price</p>
                  <p className="font-light">{assetPrice} ETH</p>
                </div>

                <div className="text-right">
                  <p className="text-gray">Highest Bid</p>
                  <p className="font-light">{assetHighestBid} wETH</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Link>
    );
  },
);

export default Card;
