import ListCards from "@/components/ListCards/ListCards";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { GetAllAssetDetailFrom1Author } from "@/utils/ReactQuery";
import React, { HTMLAttributes } from "react";

export interface TagBarProps extends HTMLAttributes<HTMLDivElement> {
  id: string;
}

const TagBar = React.forwardRef<HTMLDivElement, TagBarProps>(
  ({ className, id, ...props }, ref) => {
    return (
      <div className={className} ref={ref} {...props}>
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
            <ListCards
              queryOption={GetAllAssetDetailFrom1Author}
              param={id}
              limit={15}
            />
          </TabsContent>
          <TabsContent value="Owned"></TabsContent>
          <TabsContent value="Collections" />
        </Tabs>
      </div>
    );
  },
);

export default TagBar;
