import { Input } from "@/components/ui/input";
import SearchIcon from "@/assets/Search.svg?react";

export default function HeadLine() {
  return (
    <h1 className="gap-4 py-12">
      <b>Browse marketplace</b>
      <p className="text-xl font-medium">
        Browse through more than 50k NFTs on the NFT Marketplace.
      </p>
      <Input
        placeholder="Search your favourite NFTs"
        className="h-12 rounded-xl border-gray bg-background placeholder:text-gray focus-visible:border-foreground"
        EndIcon={
          <SearchIcon className="mr-4 w-6 cursor-pointer fill-slate-100" />
        }
      ></Input>
    </h1>
  );
}
