import Card from "@/components/Card/Card";

export default function ListCards() {
  return (
    <div className="flex w-full flex-col items-center justify-center gap-10 bg-background2 px-10 py-14">
      <Card />
      <Card />
      <Card />
      <Card />
    </div>
  );
}
