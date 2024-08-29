import { Input } from "@/components/ui/input";
import SearchIcon from "@/assets/Search.svg?react";
import { useEffect, useState } from "react";

import _ from "lodash";
import { Asset } from "@/types/Asset.type";
import { FetchSearchAssetsAPI } from "@/apis/query/AssetAPI";

export default function HeadLine() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResult, setSearchResult] = useState<Asset[]>();

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };
  const debounceSearchAssets = _.debounce(FetchSearchAssetsAPI, 500);
  useEffect(() => {
    const fetchData = async () => {
      if (searchTerm.trim() != "") {
        setSearchResult(
          (await FetchSearchAssetsAPI(searchTerm, 5)).data.result,
        );
      }
    };
    fetchData();
  }, [searchTerm]);
  return (
    <h1 className="gap-4 py-12">
      <b>Browse marketplace</b>
      <p className="text-xl font-medium">
        Browse through more than 50k NFTs on the NFT Marketplace
      </p>
      <form className="rounded-2xl ">
        <Input
          onChange={handleChange}
          placeholder="Search your favourite NFTs"
          className={`h-12 rounded-xl ${searchResult ? "rounded-b-none" : ""} bg-background placeholder:text-gray`}
          EndIcon={
            <SearchIcon className="mr-4 w-6 cursor-pointer fill-slate-100" />
          }
        ></Input>
        <ul className="flex flex-col">
          {searchResult != undefined
            ? searchResult?.map((asset) => (
                <li className="h-20 border-t">{asset.name}</li>
              ))
            : null}
        </ul>
      </form>
    </h1>
  );
}
