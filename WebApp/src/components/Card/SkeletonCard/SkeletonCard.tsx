import { Skeleton } from "@/components/ui/skeleton";

export default function SkeletonCard() {
  return (
    <li className="w-[320px] rounded-3xl bg-background">
      <Skeleton className="h-[285px] w-full rounded-b-none rounded-t-3xl" />
      <div className="flex flex-col gap-5 p-5">
        <Skeleton className="h-8 w-32" />
        <div className="flex items-center gap-5">
          <Skeleton className="h-10 w-10 rounded-full" />
          <Skeleton className="h-5 w-56" />
        </div>

        <div>
          <div className="flex justify-between">
            <div className="flex flex-col gap-3">
              <Skeleton className="h-4 w-16" />
              <Skeleton className="h-4 w-28" />
            </div>

            <div className="flex flex-col items-end gap-3">
              <Skeleton className="h-4 w-16" />
              <Skeleton className="h-4 w-28" />
            </div>
          </div>
        </div>
      </div>
    </li>
  );
}
