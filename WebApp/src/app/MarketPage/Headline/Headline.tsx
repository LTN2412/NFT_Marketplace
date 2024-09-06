import { useCallback, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";

import { FetchSearchAssetsAPI } from "@/apis/query/AssetAPI";
import SearchIcon from "@/assets/Search.svg?react";
import { Asset } from "@/types/Asset.type";

import { Input } from "@/components/ui/input";

export default function HeadLine() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResult, setSearchResult] = useState<Asset[]>();
  const searchTimeoutRef = useRef<NodeJS.Timeout | null>(null);

  const performSearch = useCallback(async (term: string) => {
    if (term.trim() !== "") {
      const result = await FetchSearchAssetsAPI(term, 5);
      setSearchResult(result.data.result);
    } else {
      setSearchResult(undefined);
    }
  }, []);

  useEffect(() => {
    if (searchTimeoutRef.current) {
      clearTimeout(searchTimeoutRef.current);
    }

    searchTimeoutRef.current = setTimeout(() => {
      performSearch(searchTerm);
    }, 300);

    return () => {
      if (searchTimeoutRef.current) {
        clearTimeout(searchTimeoutRef.current);
      }
    };
  }, [searchTerm, performSearch]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setSearchTerm(value);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (
      event.key === "Backspace" &&
      (searchTerm.length === 1 || searchTerm.length === 0)
    ) {
      setSearchResult(undefined);
    }
  };

  return (
    <h1 className="gap-4 py-12">
      <b>Browse marketplace</b>
      <p className="text-xl font-medium">
        Browse through more than 50k NFTs on the NFT Marketplace
      </p>
      <form className="rounded-2xl">
        <Input
          onChange={handleChange}
          onKeyDown={handleKeyDown}
          value={searchTerm}
          placeholder="Search your favourite NFTs"
          className={`h-12 rounded-xl ${searchResult ? "rounded-b-none" : ""} bg-background placeholder:text-gray`}
          EndIcon={
            <SearchIcon className="mr-4 w-6 cursor-pointer fill-slate-100" />
          }
        />
        {searchResult && searchResult.length > 0 && (
          <ul className="flex flex-col">
            {searchResult.map((asset) => (
              <li key={asset.id} className="h-20 border-t">
                <Link to={`/asset/${asset.id}`}>{asset.name}</Link>
              </li>
            ))}
          </ul>
        )}
      </form>
    </h1>
  );
}
