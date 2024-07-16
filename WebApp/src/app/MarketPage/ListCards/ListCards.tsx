import Card from "@/components/Card/Card";

export default function ListCards() {
  const test = [1, 2];
  return (
    <div className="grid grid-cols-1 justify-center justify-items-center gap-10 bg-background2 px-10 py-14 md:grid-cols-2 lg:grid-cols-3">
      {test.map(() => (
        <Card
          assetName={""}
          srcAsset={""}
          assetPrice={""}
          assetHighestBid={""}
          srcAvatar={""}
          authorName={""}
        />
      ))}
    </div>
  );
}
