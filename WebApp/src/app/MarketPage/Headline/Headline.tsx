import { Input } from "@/components/ui/input";
import SearchIcon from "@/assets/Search.svg?react";

export default function Headline() {
  return (
    <div className="mx- flex flex-col gap-4 bg-background px-10 py-12">
      <p className="whitespace-nowrap text-[43px] font-bold">
        Browse marketplace
      </p>
      <p className="text-xl">
        Browse through more than 50k NFTs on the NFT Marketplace.
      </p>
      <Input
        placeholder="Search your favourite NFTs"
        className="h-12 rounded-xl border-gray bg-background placeholder:text-gray focus-visible:border-foreground"
        EndIcon={
          <SearchIcon className="mr-4 w-6 cursor-pointer fill-slate-100" />
        }
      ></Input>
    </div>
  );
}
