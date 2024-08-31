import React, { HTMLAttributes } from "react";
import { cn } from "@/lib/utils";
import { useQuery } from "@tanstack/react-query";
import { Link } from "react-router-dom";

import { GetCartUserById } from "@/apis/query-options/UserQuery";

import CartIcon from "@/assets/Cart.svg?react";

export interface CratCircleProps extends HTMLAttributes<HTMLDivElement> {}

const CartCircle = React.forwardRef<HTMLDivElement, CratCircleProps>(
  ({ className, ...props }, ref) => {
    const { data } = useQuery(GetCartUserById());
    const numberAssets = data?.data.result.length || 0;
    return (
      <Link to={"/cart"} className="fixed bottom-3 left-3">
        <div
          className={cn(
            "relative flex h-14 w-14 cursor-pointer justify-center rounded-full border-2 border-gray bg-gray p-2",
            className,
          )}
          ref={ref}
          {...props}
        >
          <CartIcon className="w-8 stroke-lightGray" />
          {numberAssets ? (
            <div className="absolute -right-1.5 -top-1.5 h-5 w-5 rounded-full bg-purple text-center text-sm">
              {numberAssets}
            </div>
          ) : null}
        </div>
      </Link>
    );
  },
);

export default CartCircle;
