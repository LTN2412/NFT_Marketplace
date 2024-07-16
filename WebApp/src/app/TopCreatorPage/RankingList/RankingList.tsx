import CardCreator from "./CardCreator/CardCreator";

export default function RankingList() {
  return (
    <div className="flex w-full flex-col gap-6 px-10">
      <div className="grid w-full grid-cols-7 items-center justify-items-center gap-3 rounded-3xl border-2 border-background2 bg-background px-6 py-4 text-gray">
        <p className="col-span-1">#</p>
        <p className="col-span-4 justify-self-start">Artists</p>
        <p className="col-span-2 justify-self-start">Volumne</p>
      </div>
      <CardCreator />
      <CardCreator />
      <CardCreator />
      <CardCreator />
      <CardCreator />
    </div>
  );
}
