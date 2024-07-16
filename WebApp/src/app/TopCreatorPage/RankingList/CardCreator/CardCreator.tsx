import { Avatar } from "@/components/ui/avatar";
import FirstCharName from "@/utils/FirstCharName";
import { AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";

const mockData = {
  authorName: "Le Thanh Nhat",
  volumne: "12.4",
};
export default function CardCreator() {
  return (
    <div className="grid w-full grid-cols-7 items-center justify-items-center gap-3 rounded-3xl bg-background2 px-6 py-4">
      <p className="text-gray">1</p>
      <Avatar className="">
        <AvatarImage src="https://github.com/shadcn.png"></AvatarImage>
        <AvatarFallback>{FirstCharName(mockData.authorName)}</AvatarFallback>
      </Avatar>
      <p className="col-span-3 justify-self-start whitespace-nowrap font-semibold">
        {mockData.authorName}
      </p>
      <p className="col-span-2 justify-self-start font-mono">
        {mockData.volumne} ETH
      </p>
    </div>
  );
}
