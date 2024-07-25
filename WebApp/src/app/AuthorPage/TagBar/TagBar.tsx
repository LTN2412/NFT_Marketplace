import ListCards from "@/components/ListCards/ListCards";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

export default function TagBar() {
  return (
    <div>
      <Tabs
        defaultValue="Created"
        className="border-t border-gray bg-background"
      >
        <TabsList className="h-20 w-full px-10 md:px-16 lg:px-24">
          <TabsTrigger
            value="Created"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>Created</p>
          </TabsTrigger>
          <TabsTrigger
            value="Owned"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>Owned</p>
          </TabsTrigger>
          <TabsTrigger
            value="Collections"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>Collections</p>
          </TabsTrigger>
        </TabsList>
        <TabsContent value="Created">
          <ListCards />
        </TabsContent>
        <TabsContent value="Owned"></TabsContent>
        <TabsContent value="Collections" />
      </Tabs>
    </div>
  );
}
