import React from "react";
import GlobalIcon from "@/assets/Global.svg?react";
import ArrowRightIcon from "@/assets/ArrowRight.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import { AvatarFallback } from "@radix-ui/react-avatar";
import FirstCharName from "@/utils/FirstCharName";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  GetAllAssetsByTag,
  GetAssetDetail,
} from "@/apis/query-options/AssetQuery";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import LoadingPage from "@/app/LoadingPage/LoadingPage";
import ListCardsTest from "@/components/ListCards/ListCards";
import { AddUserAsset } from "@/types/User.type";
import { AddCartItemAPI } from "@/apis/query/UserAPI";

export interface AssetDetailProps extends React.HTMLAttributes<HTMLDivElement> {
  assetId: string;
}

const AssetDetail = React.forwardRef<HTMLDivElement, AssetDetailProps>(
  ({ className, assetId, ...props }, ref) => {
    const { data, isError, isLoading } = useQuery(GetAssetDetail(assetId));
    const { mutate } = useMutation({
      mutationFn: (addUserAsset: AddUserAsset) => {
        return AddCartItemAPI(addUserAsset);
      },
      onSuccess: () => {
        alert("Success");
      },
    });
    if (isLoading) return <LoadingPage />;
    if (isError) return <ErrorPage />;
    const asset = data!.data.result;
    const addUserAsset: AddUserAsset = {
      id: asset.id,
      name: asset.name,
      price: asset.price,
      quantity: 1,
      imgPath: asset.imgPath,
      userId: "91d313d7-6e4a-4164-a846-80379b994794",
    };
    return (
      <div className={cn(className)} ref={ref} {...props}>
        <div>
          <img
            src={"/test.png"}
            className="aspect-[16/9] w-full bg-black object-center"
          ></img>
        </div>
        <div className="flex flex-col gap-4 bg-background px-8 py-12">
          <div className="flex items-center justify-between">
            <div className="flex flex-col gap-4">
              <p className="text-5xl font-bold">{asset.name}</p>
              <p className="text-gray">Minted on {asset.timestampCreate}</p>
              <p className="font-mono text-gray">Created By</p>
              <Link
                to={`/author/${asset.authorId}`}
                className="flex w-fit items-center gap-4"
              >
                <Avatar>
                  <AvatarImage
                    src={asset.authorAvatarPath}
                    className="w-10 rounded-3xl"
                  />
                  <AvatarFallback className="w-10 content-center bg-red-900 text-center text-foreground">
                    {FirstCharName(asset.authorName)}
                  </AvatarFallback>
                </Avatar>
                <p className="font-mono">{asset.authorName}</p>
              </Link>
            </div>
            <Button
              className="flex h-14 items-center gap-2 rounded-full bg-purple px-8 text-xl hover:bg-purple lg:mr-40"
              onClick={() => mutate(addUserAsset)}
            >
              Add to cart
              <PlusIcon className="w-6 fill-foreground" />
            </Button>
          </div>
          <p className="font-mono text-gray">Description</p>
          <p className="">{asset.description}</p>
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
            {asset.tags.map((tag) => (
              <div className="w-fit rounded-3xl bg-background2 px-5 py-3">
                {tag}
              </div>
            ))}
          </div>

          <p className="text-3xl font-bold">More from this Artist</p>
          <ListCardsTest
            queryOption={GetAllAssetsByTag}
            param={asset.tags[0]}
            limit={15}
          />
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
