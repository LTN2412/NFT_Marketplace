import CardCreator from "./CardCreator/CardCreator";

export default function RankingList() {
  return (
    <h1 className="gap-6">
      <div className="grid w-full grid-cols-7 items-center justify-items-start gap-3 rounded-3xl border-2 border-background2 bg-background px-6 py-4 text-gray md:grid-cols-9 lg:grid-cols-11">
        <p className="col-span-1 justify-self-center">#</p>
        <p className="col-span-4">Artists</p>
        <p className="col-span-2 max-md:hidden">Change</p>
        <p className="col-span-2 max-lg:hidden">NFTs Sold</p>
        <p className="col-span-2">Volumne</p>
      </div>
      <CardCreator
        number={1}
        authorName={"Le Nhat"}
        volumne={200}
        change={1.75}
        sold={800}
      />
      <CardCreator
        number={1}
        authorName={"Le Nhat"}
        volumne={200}
        change={0}
        sold={800}
      />
      <CardCreator
        number={1}
        authorName={"Le Nhat"}
        volumne={200}
        change={-19}
        sold={800}
      />
    </h1>
  );
}
