import React from "react";
import GlobalIcon from "@/assets/Global.svg?react";
import ArrowRightIcon from "@/assets/ArrowRight.svg?react";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import ListCards from "@/components/ListCards/ListCards";
import { AvatarFallback } from "@radix-ui/react-avatar";
import FirstCharName from "@/utils/FirstCharName";

export interface AssetDetailProps extends React.HTMLAttributes<HTMLDivElement> {
  assetSrc: string;
  assetName: string;
  mintedAt: string;
  authorAvatar: string;
  authorName: string;
  description: string;
  tags: string[];
}

const AssetDetail = React.forwardRef<HTMLDivElement, AssetDetailProps>(
  (
    {
      className,
      assetSrc,
      assetName,
      mintedAt,
      authorAvatar,
      authorName,
      description,
      tags,
      ...props
    },
    ref,
  ) => {
    return (
      <div className={cn(className)} ref={ref} {...props}>
        <div>
          <img src={assetSrc} className="object-cover"></img>
        </div>
        <div className="flex flex-col gap-4 bg-background px-8 py-12">
          <p className="text-5xl font-bold">{assetName}</p>
          <p className="text-gray">Minted on {mintedAt}</p>
          <p className="font-mono text-gray">Created By</p>
          <div className="flex items-center gap-4">
            <Avatar>
              <AvatarImage src={authorAvatar} className="w-10 rounded-3xl" />
              <AvatarFallback className="text-black">
                {FirstCharName(authorName)}
              </AvatarFallback>
            </Avatar>
            <p className="font-mono">{authorName}</p>
          </div>
          <p className="font-mono text-gray">Description</p>
          <p className="">{description}</p>
          <p className="font-mono text-gray">Detail</p>
          <Link
            to={""}
            className="flex w-fit cursor-pointer items-center gap-2"
          >
            <GlobalIcon className="w-10 fill-gray" />
            <p>View on EtherScan</p>
          </Link>
          <Link
            to={""}
            className="flex w-fit cursor-pointer items-center gap-2"
          >
            <GlobalIcon className="w-10 fill-gray" />
            <p>View Original</p>
          </Link>
          <p className="font-mono text-gray">Tags</p>
          <div className="flex w-fit cursor-pointer flex-col gap-4">
            {tags.map((tag) => (
              <div className="w-fit rounded-3xl bg-background2 px-5 py-3">
                {tag}
              </div>
            ))}
          </div>

          <p className="text-3xl font-bold">More from this Artist</p>
          <ListCards />
          <Button className="h-16 w-full rounded-xl border-2 border-purple bg-background">
            <ArrowRightIcon className="w-10 fill-purple" />
            <p>Go To Artist Page</p>
          </Button>
        </div>
      </div>
    );
  },
);

export default AssetDetail;
