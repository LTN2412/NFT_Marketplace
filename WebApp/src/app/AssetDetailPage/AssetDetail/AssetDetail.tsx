import React from "react";
import { Link } from "react-router-dom";

import {
  GetAllAssetsByTag,
  GetAssetDetail,
} from "@/apis/query-options/AssetQuery";
import { AddCartItemAPI } from "@/apis/query/UserAPI";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import LoadingPage from "@/app/LoadingPage/LoadingPage";
import ArrowRightIcon from "@/assets/ArrowRight.svg?react";
import GlobalIcon from "@/assets/Global.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import { useToast } from "@/hooks/use-toast";
import { cn } from "@/lib/utils";
import { UserAsset } from "@/types/User.type";
import { AvatarFallback } from "@radix-ui/react-avatar";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import ListCards from "@/components/ListCards/ListCards";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";

import FirstCharName from "@/utils/FirstCharName";
import formatDate from "@/utils/FormatDate";

import ListComment from "./ListComment/ListComment";

export interface AssetDetailProps extends React.HTMLAttributes<HTMLDivElement> {
  assetId: string;
  isLogin: boolean;
}

const AssetDetail = React.forwardRef<HTMLDivElement, AssetDetailProps>(
  ({ className, assetId, isLogin, ...props }, ref) => {
    const queryClient = useQueryClient();
    const { toast } = useToast();
    const { data, isError, isLoading } = useQuery(GetAssetDetail(assetId));
    const { mutate } = useMutation({
      mutationFn: (addUserAsset: UserAsset) => AddCartItemAPI(addUserAsset),
      onSuccess: () => {
        toast({
          title: "Success",
          description: "Asset is in your cart.",
        });
        queryClient.invalidateQueries({ queryKey: ["cart"] });
      },
    });
    if (isLoading) return <LoadingPage />;
    if (isError) return <ErrorPage />;
    const asset = data!.data.result;
    const addUserAsset: UserAsset = {
      id: asset.id,
      name: asset.name,
      price: asset.price,
      quantity: 1,
      imgPath: asset.imgPath,
      isSelect: true,
    };
    return (
      <div className={cn(className)} ref={ref} {...props}>
        <div className="flex justify-center bg-background">
          <img
            src={"/test.png"}
            className="w-full object-contain md:w-4/5 lg:h-[500px] lg:w-auto"
          />
        </div>
        <div className="flex flex-col gap-4 bg-background px-5 py-12 md:px-12 lg:px-24">
          <div className="flex items-center justify-between">
            <div className="flex flex-col gap-4">
              <p className="text-5xl font-bold">{asset.name}</p>
              <p className="text-gray">
                Minted on {formatDate(asset.updatedAt)}
              </p>
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
                  <AvatarFallback className="w-10 content-center text-center text-foreground">
                    {FirstCharName(asset.authorName)}
                  </AvatarFallback>
                </Avatar>
                <p className="font-mono">{asset.authorName}</p>
              </Link>
            </div>
            {isLogin && (
              <Button
                className="flex h-14 w-fit items-center gap-2 rounded-full bg-purple px-8 text-xl hover:bg-purple"
                onClick={() => mutate(addUserAsset)}
              >
                Add
                <PlusIcon className="w-6 fill-foreground" />
              </Button>
            )}
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

          <p className="text-3xl font-bold">Comment</p>
          <ListComment assetId={assetId} isLogin={isLogin} />
          <p className="text-3xl font-bold">You may also like this</p>
          <ListCards queryOption={GetAllAssetsByTag} param={asset.tags[0]} />
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
