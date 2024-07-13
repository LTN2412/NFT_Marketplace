import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import ListCards from "../ListCards/ListCards";

export default function TabBar() {
  return (
    <div>
      <Tabs defaultValue="NFTs" className="border-t border-gray bg-background">
        <TabsList className="h-20 w-full px-10">
          <TabsTrigger
            value="NFTs"
            className="text -gray  w-1/2 pb-5 data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p className="pr-4">NFTs</p>
            <div className="h-6 w-14 rounded-3xl bg-lightGray font-light">
              312
            </div>
          </TabsTrigger>
          <TabsTrigger
            value="Collections"
            className="w-1/2 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p className="pr-4">Collections</p>
            <div className="h-6 w-14 rounded-3xl bg-lightGray font-light">
              123
            </div>
          </TabsTrigger>
        </TabsList>
        <TabsContent value="NFTs">
          <ListCards />
        </TabsContent>
        <TabsContent value="Collections">
          <ListCards />
        </TabsContent>
      </Tabs>
    </div>
  );
}
