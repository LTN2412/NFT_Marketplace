import React, { HTMLAttributes, useState } from "react";
import { Link } from "react-router-dom";

import { DeleteCartItemAPI, UpdateIsSelectItemAPI } from "@/apis/query/UserAPI";
import DeleteIcon from "@/assets/Delete.svg?react";
import MinusIcon from "@/assets/Minus.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import { useToast } from "@/hooks/use-toast";
import { cn } from "@/lib/utils";
import { useMutation, useQueryClient } from "@tanstack/react-query";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

export interface CartItemProps extends HTMLAttributes<HTMLDivElement> {
  assetId: string;
  index: number;
  name: string;
  price: number;
  quantityDB: number;
  imgPath: string;
  isSelect: boolean;
}

const CartItem = React.forwardRef<HTMLDivElement, CartItemProps>(
  (
    {
      className,
      assetId,
      name,
      price,
      quantityDB,
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      imgPath,
      isSelect,
      ...props
    },
    ref,
  ) => {
    const queryClient = useQueryClient();
    const { toast } = useToast();
    const [quantity, setQuantity] = useState(quantityDB);
    const [selected, setSelected] = useState(isSelect);

    const { mutate: deleteCartItem } = useMutation({
      mutationFn: (assetId: string) => DeleteCartItemAPI(assetId),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["cart"] });
        toast({
          title: "Success",
          description: "Remove asset success.",
        });
      },
    });

    const { mutate: updateCartItemSelect } = useMutation({
      mutationFn: ({
        assetId,
        isSelect,
      }: {
        assetId: string;
        isSelect: boolean;
      }) => UpdateIsSelectItemAPI(assetId, isSelect),
      onSuccess: () => queryClient.invalidateQueries({ queryKey: ["cart"] }),
    });

    const handleIncrement = () => {
      setQuantity(quantity + 1);
    };

    const handleDecrement = () => {
      if (quantity > 1) {
        setQuantity(quantity - 1);
      }
    };

    const handleDelete = () => {
      deleteCartItem(assetId);
    };

    const handleSelectToggle = () => {
      const newSelectState = !selected;
      setSelected(newSelectState);
      updateCartItemSelect({ assetId, isSelect: newSelectState });
    };

    return (
      <div
        className={cn(
          "grid grid-cols-12 items-center justify-items-center gap-3 rounded-3xl border-2 border-background2 px-6 py-4",
          className,
        )}
        ref={ref}
        {...props}
      >
        <button
          className={`col-span-1 h-6 w-6 justify-self-center rounded-full border-2 ${
            selected ? "border-purple bg-purple" : "border-gray"
          }`}
          onClick={handleSelectToggle}
        />
        <Link
          to={`/asset/${assetId}`}
          className="col-span-2 justify-self-start"
        >
          <img
            src={"/test.png"}
            className="aspect-[320/285] h-full w-20 object-cover"
            alt={name}
          />
        </Link>
        <p className="col-span-3 justify-self-start">{name}</p>
        <p className="col-span-2">{price} ETH</p>
        <div className="col-span-1 flex w-full items-center gap-4">
          <MinusIcon
            className="cursor-pointer stroke-gray hover:opacity-70"
            onClick={handleDecrement}
          />
          <p>{quantity}</p>
          <PlusIcon
            className="cursor-pointer fill-gray hover:opacity-70"
            onClick={handleIncrement}
          />
        </div>
        <p className="col-span-2">{price * quantity} ETH</p>
        <AlertDialog>
          <AlertDialogTrigger asChild>
            <DeleteIcon className="col-span-1 h-8 w-8 cursor-pointer justify-self-end stroke-gray" />
          </AlertDialogTrigger>
          <AlertDialogContent className="border-0">
            <AlertDialogHeader>
              <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
              <AlertDialogDescription>
                This action cannot be undone. This will permanently delete this
                item from your cart.
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancel</AlertDialogCancel>
              <AlertDialogAction
                onClick={handleDelete}
                className="bg-purple hover:bg-purple"
              >
                Delete
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </div>
    );
  },
);

export default CartItem;
