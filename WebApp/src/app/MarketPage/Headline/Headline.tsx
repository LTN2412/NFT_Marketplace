import { useCallback, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";

import { FetchSearchAssetsAPI } from "@/apis/query/AssetAPI";
import { FetchSearchAuthorsAPI } from "@/apis/query/AuthorAPI";
import SearchIcon from "@/assets/Search.svg?react";
import { Asset } from "@/types/Asset.type";
import { Author } from "@/types/Author.type";

import { Input } from "@/components/ui/input";

export default function HeadLine() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchAssetResult, setSearchAssetResult] = useState<Asset[]>([]);
  const [searchAuthorResult, setSearchAuthorResult] = useState<Author[]>([]);
  const searchTimeoutRef = useRef<NodeJS.Timeout | null>(null);

  const performSearch = useCallback(async (term: string) => {
    if (term.trim() !== "") {
      const [assetResult, authorResult] = await Promise.all([
        FetchSearchAssetsAPI(term, 5),
        FetchSearchAuthorsAPI(term, 5),
      ]);
      setSearchAssetResult(assetResult.data.result);
      setSearchAuthorResult(authorResult.data.result);
    } else {
      setSearchAssetResult([]);
      setSearchAuthorResult([]);
    }
  }, []);

  useEffect(() => {
    if (searchTimeoutRef.current) clearTimeout(searchTimeoutRef.current);
    searchTimeoutRef.current = setTimeout(() => {
      performSearch(searchTerm);
    }, 300);

    return () => {
      if (searchTimeoutRef.current) clearTimeout(searchTimeoutRef.current);
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
      setSearchAssetResult([]);
      setSearchAuthorResult([]);
    }
  };

  const hasResults =
    searchAssetResult.length > 0 || searchAuthorResult.length > 0;

  return (
    <h1 className="gap-4 py-12">
      <h3>Browse marketplace</h3>
      <p className="text-xl font-medium">
        Browse through more than 50k NFTs on the NFT Marketplace
      </p>
      <form className="rounded-2xl">
        <Input
          onChange={handleChange}
          onKeyDown={handleKeyDown}
          value={searchTerm}
          placeholder="Search your favourite NFTs or Authors"
          className={`h-12 rounded-xl ${hasResults ? "rounded-b-none" : ""} bg-background placeholder:text-gray`}
          EndIcon={
            <SearchIcon className="mr-4 w-6 cursor-pointer fill-slate-100" />
          }
        />
        {hasResults && (
          <div className="rounded-b-xl border-t bg-background">
            {searchAssetResult.length > 0 && (
              <ul className="flex flex-col">
                <li className="border-b px-4 py-2 font-semibold">Assets</li>
                {searchAssetResult.map((asset) => (
                  <li key={asset.id} className="hover:bg-gray-100 px-4 py-2">
                    <Link to={`/asset/${asset.id}`}>{asset.name}</Link>
                  </li>
                ))}
              </ul>
            )}
            {searchAuthorResult.length > 0 && (
              <ul className="flex flex-col">
                <li className="border-b px-4 py-2 font-semibold">Authors</li>
                {searchAuthorResult.map((author) => (
                  <li key={author.id} className="hover:bg-gray-100 px-4 py-2">
                    <Link to={`/author/${author.id}`}>{author.name}</Link>
                  </li>
                ))}
              </ul>
            )}
          </div>
        )}
      </form>
    </h1>
  );
}
