import { Avatar } from "@/components/ui/avatar";
import { AvatarImage } from "@radix-ui/react-avatar";

export default function CardInfo() {
  return (
    <div className="flex flex-col gap-5 p-5">
      <p className="text-xl font-bold">Magic Mushorom</p>
      <div className="flex items-center gap-2">
        <Avatar>
          <AvatarImage
            src="https://github.com/shadcn.png"
            className="w-56 rounded-full"
          />
        </Avatar>
        <p>LTN2412</p>
      </div>
      <div className="flex justify-between">
        <div className="text-left">
          <p className="text-gray">Price</p>
          <p className="font-light">5.0 ETH</p>
        </div>

        <div className="text-right">
          <p className="text-gray">Highest Bid</p>
          <p className="font-light">9.0 wETH</p>
        </div>
      </div>
    </div>
  );
}
