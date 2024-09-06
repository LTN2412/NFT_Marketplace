import { Skeleton } from "@/components/ui/skeleton";

export default function SkeletonCardCreator() {
  return (
    <div className="grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl bg-background2 px-6 py-4 md:grid-cols-9 lg:grid-cols-11">
      <Skeleton className="h-8 w-6 justify-self-center"></Skeleton>
      <Skeleton className="h-12 w-12 rounded-full"></Skeleton>
      <Skeleton className="col-span-3 h-8 w-44 max-sm:ml-4 max-sm:w-28"></Skeleton>
      <Skeleton className="col-span-2 h-8 w-24 max-md:hidden"></Skeleton>
      <Skeleton className="col-span-2 h-8 w-24 max-lg:hidden"></Skeleton>
      <Skeleton className="col-span-2 h-8 w-24 max-sm:w-14"></Skeleton>
    </div>
  );
}
