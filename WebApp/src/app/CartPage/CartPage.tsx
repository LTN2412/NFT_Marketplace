import Navbar from "@/components/Navbar/Navbar";
import HeadLine from "./HeadLine/HeadLine";
import ListCartItems from "@/components/ListCartItems/ListCartItems";

export default function CartPage() {
  return (
    <div className="absolute h-screen w-full bg-background">
      <Navbar />
      <HeadLine />
      <ListCartItems />
    </div>
  );
}
