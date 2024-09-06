import React, { HTMLAttributes } from "react";

import { GetAllAssetDetailFrom1Author } from "@/apis/query-options/AssetQuery";

import ListCards from "@/components/ListCards/ListCards";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

export interface TagBarProps extends HTMLAttributes<HTMLDivElement> {
  authorId: string;
}

const TagBar = React.forwardRef<HTMLDivElement, TagBarProps>(
  ({ className, authorId, ...props }, ref) => {
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
              param={authorId}
            />
          </TabsContent>
          <TabsContent value="Owned">
            <ListCards
              queryOption={GetAllAssetDetailFrom1Author}
              param={authorId}
            />
          </TabsContent>
          <TabsContent value="Collections">
            <ListCards
              queryOption={GetAllAssetDetailFrom1Author}
              param={authorId}
            />
          </TabsContent>
        </Tabs>
      </div>
    );
  },
);

export default TagBar;
