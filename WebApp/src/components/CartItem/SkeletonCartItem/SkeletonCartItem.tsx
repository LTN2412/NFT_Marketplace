import { Skeleton } from "@/components/ui/skeleton";

export default function SkeletonCartItem() {
  return (
    <li className="grid grid-cols-12 items-center justify-items-center gap-3 rounded-3xl border-2 border-background2 px-6 py-4">
      <Skeleton className="col-span-1 h-6 w-6 justify-self-center"></Skeleton>
      <Skeleton className="col-span-2 aspect-[320/285] w-20 justify-self-start"></Skeleton>
      <Skeleton className="col-span-3 h-6 w-60 justify-self-start"></Skeleton>
      <Skeleton className="col-span-2 h-6 w-24"></Skeleton>
      <Skeleton className="col-span-1 flex h-6 w-16 items-center gap-4"></Skeleton>
      <Skeleton className="col-span-2 h-6 w-24"></Skeleton>
      <Skeleton className="col-span-1 h-8 w-8 justify-self-end "></Skeleton>
    </li>
  );
}
