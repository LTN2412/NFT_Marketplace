import ListCartItems from "@/components/ListCartItems/ListCartItems";
import Navbar from "@/components/Navbar/Navbar";

import HeadLine from "./HeadLine/HeadLine";

export default function CartPage() {
  return (
    <div className="absolute h-screen w-full bg-background pt-[70px] lg:pt-[100px]">
      <Navbar />
      <HeadLine />
      <ListCartItems />
    </div>
  );
}
