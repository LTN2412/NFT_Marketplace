import { Avatar, AvatarImage } from "@/components/ui/avatar";

export default function HighlightNFT() {
  return (
    <div className="row-span-3 aspect-square w-3/4 shadow-xl md:w-full lg:min-w-[300px]">
      <img
        className="h-2/3 rounded-t-2xl bg-[url('/1.jpg')] bg-contain lg:w-full "
        src="/2.png"
      />
      <div className="flex h-1/3 w-full flex-col justify-center gap-2 rounded-b-2xl bg-background2 px-6 lg:gap-6">
        <b className="text-2xl md:text-3xl lg:text-4xl">Space Walking</b>
        <div className="flex items-center gap-3 lg:gap-5">
          <Avatar className="md:h-14 md:w-14 lg:h-16 lg:w-16">
            <AvatarImage src="https://github.com/shadcn.png" />
          </Avatar>
          <p className="text-xl md:text-2xl lg:text-3xl">LTN</p>
        </div>
      </div>
    </div>
  );
}
