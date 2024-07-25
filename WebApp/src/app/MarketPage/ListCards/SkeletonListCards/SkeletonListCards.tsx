import SkeletonCard from "@/components/Card/SkeletonCard/SkeletonCard";

interface SkeletonListCardsProps {
  limit: number[];
}

export default function SkeletonListCards({ limit }: SkeletonListCardsProps) {
  return (
    <div>
      <div className="grid grid-cols-1 justify-center justify-items-center gap-10 bg-background2 px-10 py-12 md:grid-cols-2 lg:grid-cols-3">
        {limit.map(() => (
          <SkeletonCard></SkeletonCard>
        ))}
      </div>
    </div>
  );
}
