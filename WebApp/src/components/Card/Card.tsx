import CardInfo from "./CardInfo/CardInfo";

export default function Card() {
  return (
    <div className="w-[320px] rounded-3xl bg-background">
      <div className="rounded-t-3xl">
        <img src="/test.png" className="object-cover"></img>
      </div>
      <CardInfo />
    </div>
  );
}
