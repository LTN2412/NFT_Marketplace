import SkeletonCartItem from "@/components/CartItem/SkeletonCartItem/SkeletonCartItem";

interface SkeletonListCartItemsProps {
  limit: number;
}

export default function SkeletonListCartItems({
  limit,
}: SkeletonListCartItemsProps) {
  return (
    <ul className="flex flex-col gap-6 bg-background px-10 py-8 md:px-16 lg:px-24">
      <li
        key={"-1"}
        className="grid grid-cols-12 items-center justify-items-center gap-3 rounded-3xl border-2 border-background2 px-6 py-4 text-gray"
      >
        <p className="col-span-1 justify-self-center">#</p>
        <p className="col-span-2 justify-self-start">Asset</p>
        <p className="col-span-3 justify-self-start">Name</p>
        <p className="col-span-2 ">Price</p>
        <p className="col-span-1">Quantity</p>
        <p className="col-span-2">Total</p>
        <p className="col-span-1 justify-self-end">Delete</p>
      </li>
      {Array(limit)
        .fill(0)
        .map((_, index) => (
          <SkeletonCartItem key={index}></SkeletonCartItem>
        ))}
    </ul>
  );
}
