import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import ListCards from "../ListCards/ListCards";
import { Link, useLocation } from "react-router-dom";

export default function TabBar() {
  const location = useLocation();
  const currentTab = location.pathname.split("/").pop();
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
              <div className="h-6 w-14 rounded-3xl bg-lightGray font-light text-black max-sm:hidden">
                321
              </div>
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
              <div className="h-6 w-14 rounded-3xl bg-lightGray font-light text-black max-sm:hidden">
                123
              </div>
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
