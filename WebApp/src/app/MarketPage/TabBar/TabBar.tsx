import { useQuery } from "@tanstack/react-query";
import { Link, useLocation } from "react-router-dom";

import { CountAllAssets } from "@/apis/query-options/AssetQuery";

import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import ListCards from "../ListCards/ListCards";

export default function TabBar() {
  const location = useLocation();
  const currentTab = location.pathname.split("/").pop();
  const { data } = useQuery(CountAllAssets());
  const numberAssets = data?.data.result;
  return (
    <div>
      <Tabs
        defaultValue="nfts"
        value={currentTab}
        className="border-t border-gray bg-background"
      >
        <TabsList className="h-20 w-full px-10 md:px-16 lg:px-24">
          <TabsTrigger
            value="nfts"
            className="w-1/2 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <Link
              to="/marketplace/nfts"
              className="flex w-full items-center justify-center gap-4 sm:pr-4"
            >
              NFTs
              {numberAssets && (
                <div className="h-6 w-14 rounded-3xl bg-lightGray font-light text-black max-sm:hidden">
                  {numberAssets}
                </div>
              )}
            </Link>
          </TabsTrigger>
          <TabsTrigger
            value="collections"
            className="w-1/2 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <Link
              to="/marketplace/collections"
              className="flex w-full items-center justify-center gap-4 sm:pr-4"
            >
              Collections
              {numberAssets && (
                <div className="h-6 w-14 rounded-3xl bg-lightGray font-light text-black max-sm:hidden">
                  {numberAssets}
                </div>
              )}
            </Link>
          </TabsTrigger>
        </TabsList>
        <TabsContent value="nfts">
          <ListCards />
        </TabsContent>
        <TabsContent value="collections">
          <ListCards />
        </TabsContent>
      </Tabs>
    </div>
  );
}
