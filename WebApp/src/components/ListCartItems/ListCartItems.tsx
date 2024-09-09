import { GetAllAssetsInCart } from "@/apis/query-options/AssetQuery";
import { GetCartUserById } from "@/apis/query-options/UserQuery";
import TotalSection from "@/app/CartPage/TotalSection/TotalSection";
import ErrorPage from "@/app/ErrorPage/ErrorPage";
import { useQuery } from "@tanstack/react-query";

import CartItem from "../CartItem/CartItem";
import SkeletonListCartItems from "./SkeletonListCartItems/SkeletonListCartItems";

export default function ListCartItems() {
  const {
    data: cartData,
    isLoading: isCartLoading,
    isError: isCartError,
  } = useQuery(GetCartUserById());

  const cartItems = cartData?.data?.result || [];
  const assetIds = cartItems.map((item) => item.id);

  const {
    data: assetsData,
    isLoading: isAssetsLoading,
    isError: isAssetsError,
  } = useQuery(GetAllAssetsInCart(assetIds, cartItems.length > 0));

  const isLoading = isCartLoading || (cartItems.length > 0 && isAssetsLoading);
  const isError = isCartError || (cartItems.length > 0 && isAssetsError);

  // * Merge
  const mergedCartItems = cartItems.map((cartItem) => {
    const assetDetails =
      assetsData?.data?.result?.find((asset) => asset.id === cartItem.id) || {};
    return { ...cartItem, ...assetDetails };
  });

  const totalPrice = mergedCartItems.reduce(
    (total, item) => total + (item.isSelect ? item.price * item.quantity : 0),
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
          <p className="col-span-2">Price</p>
          <p className="col-span-1">Quantity</p>
          <p className="col-span-2">Total</p>
          <p className="col-span-1 justify-self-end">Delete</p>
        </li>
        {mergedCartItems.length > 0 ? (
          mergedCartItems.map((cartItem, index) => (
            <li key={cartItem.id}>
              <CartItem
                assetId={cartItem.id}
                index={index}
                name={cartItem.name}
                price={cartItem.price}
                quantityDB={cartItem.quantity}
                imgPath={cartItem.imgPath}
                isSelect={cartItem.isSelect}
              />
            </li>
          ))
        ) : (
          <p className="text-xl font-bold">Nothing in your cart!</p>
        )}
      </ul>
      <TotalSection totalPrice={totalPrice} />
    </div>
  );
}
