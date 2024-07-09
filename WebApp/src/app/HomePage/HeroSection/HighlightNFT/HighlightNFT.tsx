import { Avatar, AvatarImage } from "@/components/ui/avatar";

export default function HighlightNFT() {
  return (
    <div className="row-span-3 aspect-square w-full shadow-xl lg:min-w-[500px]">
      <div className="h-3/4 rounded-t-2xl bg-[url('/1.jpg')] bg-contain lg:w-full " />
      <div className="flex h-1/4 w-full flex-col justify-center gap-1 rounded-b-2xl bg-background2 px-5 lg:gap-4">
        <b className="text-[20px] lg:text-[32px]">Space Walking</b>
        <div className="flex items-center gap-3">
          <Avatar className="lg:h-[50px] lg:w-[50px]">
            <AvatarImage src="https://github.com/shadcn.png" />
          </Avatar>
          <p className="text-xl">LTN</p>
        </div>
      </div>
    </div>
  );
}
