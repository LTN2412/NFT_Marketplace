import { useQuery } from "@tanstack/react-query";

import { GetCartUserById } from "@/apis/query-options/UserQuery";

import SkeletonListCartItems from "./SkeletonListCartItems/SkeletonListCartItems";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import CartItem from "../CartItem/CartItem";
import TotalSection from "@/app/CartPage/TotalSection/TotalSection";

export default function ListCartItems() {
  const { data, isLoading, isError } = useQuery(GetCartUserById());
  const cartItems = data?.data.result;
  const totalPrice = cartItems?.reduce(
    (total, item) => total + (item.price * item.quantity ?? 0),
    0,
  );
  if (isLoading) return <SkeletonListCartItems limit={5} />;
  if (isError) return <ErrorPage />;
  return (
    <div>
      <ul className="flex flex-col gap-6 bg-background px-10 py-24 pt-8 md:px-16 lg:px-24">
        <li className="grid grid-cols-12 items-center justify-items-center gap-3 rounded-3xl border-2 border-background2 px-6 py-4 text-gray">
          <p className="col-span-1 justify-self-center">#</p>
          <p className="col-span-2 justify-self-start">Asset</p>
          <p className="col-span-3 justify-self-start">Name</p>
          <p className="col-span-2 ">Price</p>
          <p className="col-span-1">Quantity</p>
          <p className="col-span-2">Total</p>
          <p className="col-span-1 justify-self-end">Delete</p>
        </li>
        {cartItems && cartItems?.length > 0 ? (
          cartItems.map((cartItem, index) => (
            <li>
              <CartItem
                key={cartItem.id}
                assetId={cartItem.id}
                index={index}
                name={cartItem.name}
                price={cartItem.price}
                quantityDB={cartItem.quantity}
                imgPath={cartItem.imgPath}
              />
            </li>
          ))
        ) : (
          <p className="text-xl font-bold">Nothing in your cart!</p>
        )}
      </ul>
      <TotalSection totalPrice={totalPrice || 0} />
    </div>
  );
}
