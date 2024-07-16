import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import RankingList from "../RankingList/RankingList";

export default function TabBar() {
  return (
    <div>
      <Tabs defaultValue="1d" className="border-t border-gray bg-background">
        <TabsList className="h-20 w-full px-10">
          <TabsTrigger
            value="1d"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>1d</p>
          </TabsTrigger>
          <TabsTrigger
            value="7d"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>7d</p>
          </TabsTrigger>
          <TabsTrigger
            value="30d"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>30d</p>
          </TabsTrigger>
          <TabsTrigger
            value="All Time"
            className="w-1/3 pb-5 text-gray data-[state=active]:border-b-2 data-[state=active]:border-lightGray"
          >
            <p>All Time</p>
          </TabsTrigger>
        </TabsList>
        <TabsContent value="1d">
          <RankingList />
        </TabsContent>
        <TabsContent value="7d"></TabsContent>
        <TabsContent value="30d"></TabsContent>
        <TabsContent value="All Time"></TabsContent>
      </Tabs>
    </div>
  );
}
