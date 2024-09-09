import { OrderAPI, RemoveAllAssetsIsSelected } from "@/apis/query/OrderAPI";
import { useToast } from "@/hooks/use-toast";
import { useMutation, useQueryClient } from "@tanstack/react-query";

import { Button } from "@/components/ui/button";

interface TotalSectionProps {
  totalPrice: number;
}

export default function TotalSection({ totalPrice }: TotalSectionProps) {
  const queryClient = useQueryClient();
  const { toast } = useToast();

  const removeSelectedAssetsMutation = useMutation({
    mutationFn: () => RemoveAllAssetsIsSelected(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["cart"] });
      toast({
        title: "Sucess",
        description: "Thank you for your order",
      });
    },
  });

  const createOrderMutation = useMutation({
    mutationFn: () => OrderAPI(),
    onSuccess: () => removeSelectedAssetsMutation.mutate(),
  });

  const handlePurchase = () => createOrderMutation.mutate();

  return (
    <div className="fixed bottom-0 flex h-20 w-full justify-end bg-background2 px-5 md:px-12 lg:px-24">
      <div className="flex items-center gap-10">
        <p className="text-3xl font-bold">Total</p>
        <p className="flex gap-4 text-2xl font-semibold text-purple">
          {totalPrice}
          <p className="self-end text-base font-bold text-foreground">ETH</p>
        </p>
        <Button
          className="h-12 w-32 rounded-3xl bg-purple text-xl hover:bg-purple"
          onClick={handlePurchase}
          disabled={
            createOrderMutation.isPending ||
            removeSelectedAssetsMutation.isPending
          }
        >
          {createOrderMutation.isPending ||
          removeSelectedAssetsMutation.isPending
            ? "Processing..."
            : "Purchase"}
        </Button>
      </div>
    </div>
  );
}
