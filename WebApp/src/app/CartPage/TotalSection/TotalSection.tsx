import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

interface TotalSectionProps {
  totalPrice: number;
}
export default function TotalSection({ totalPrice }: TotalSectionProps) {
  return (
    <div className="fixed bottom-0 flex h-20 w-full justify-between bg-background2 md:px-16 lg:px-24">
      <div className="flex items-center gap-6">
        <p className="text-xl font-bold">Apply Voucher</p>
        <Input
          className="rounded-xl text-black"
          placeholder="Enter your voucher ..."
        />
        <Button className="h-10 w-20 self-center rounded-xl bg-purple text-xl hover:bg-purple">
          Apply
        </Button>
      </div>
      <div className="flex gap-10">
        <p className="self-center text-3xl font-bold">Total</p>
        <p className="flex gap-4 self-center text-2xl text-purple">
          {totalPrice}
          <p className="self-end text-base font-bold text-foreground">ETH</p>
        </p>
        <Button className="h-12 w-32 self-center justify-self-end rounded-3xl bg-purple text-xl hover:bg-purple">
          Purchase
        </Button>
      </div>
    </div>
  );
}
