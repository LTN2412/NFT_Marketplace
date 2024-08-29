import React, { useState, HTMLAttributes } from "react";
import { Link } from "react-router-dom";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cn } from "@/lib/utils";

import { DeleteCartItemAPI } from "@/apis/query/UserAPI";

import DeleteIcon from "@/assets/Delete.svg?react";
import PlusIcon from "@/assets/Plus.svg?react";
import MinusIcon from "@/assets/Minus.svg?react";

type DeleteData = {
  userId: string;
  id: string;
};

export interface CartItemProps extends HTMLAttributes<HTMLDivElement> {
  id: string;
  index: number;
  name: string;
  price: number;
  quantityDB: number;
  imgPath: string;
  userId: string;
}

const CartItem = React.forwardRef<HTMLDivElement, CartItemProps>(
  (
    {
      className,
      id,
      index,
      name,
      price,
      quantityDB,
      imgPath,
      userId,
      ...props
    },
    ref,
  ) => {
    const [quantity, setQuantity] = useState(quantityDB);
    const queryClient = useQueryClient();
    const { mutate } = useMutation({
      mutationFn: (data: DeleteData) => DeleteCartItemAPI(data.userId, data.id),
      onSuccess: () => queryClient.invalidateQueries({ queryKey: ["cart"] }),
    });
    const handleIncrement = () => {
      setQuantity(quantity + 1);
    };
    const handleDecreament = () => {
      setQuantity(quantity - 1);
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
        <p className="col-span-1 justify-self-center">{index + 1}</p>
        <Link to={`/asset/${id}`} className="col-span-2 justify-self-start">
          <img
            src={imgPath}
            className="aspect-[320/285] h-full w-20 object-cover"
          ></img>
        </Link>
        <p className="col-span-3 justify-self-start">{name}</p>
        <p className="col-span-2">{price} ETH</p>
        <div className="col-span-1 flex w-full items-center gap-4">
          <MinusIcon
            className="cursor-pointer stroke-gray hover:opacity-70"
            onClick={handleDecreament}
          />
          <p>{quantity}</p>
          <PlusIcon
            className="cursor-pointer fill-gray hover:opacity-70"
            onClick={handleIncrement}
          />
        </div>
        <p className="col-span-2">{price * quantity} ETH</p>
        <DeleteIcon
          className="col-span-1 h-8 w-8 cursor-pointer justify-self-end stroke-gray"
          onClick={() => mutate({ userId, id })}
        />
      </div>
    );
  },
);

export default CartItem;
