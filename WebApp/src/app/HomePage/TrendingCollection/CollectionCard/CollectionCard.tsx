import { useState } from "react";

import { Avatar, AvatarImage } from "@/components/ui/avatar";

export default function CollectionCard() {
  const [numberCollections, setNumberCollections] = useState(
    window.innerWidth >= 1024 ? 3 : 1,
  );
  window.addEventListener("resize", () =>
    window.innerWidth >= 1024
      ? setNumberCollections(3)
      : setNumberCollections(1),
  );
  return (
    <ul className="flex w-full flex-col items-center justify-between gap-12 lg:flex-row">
      {Array(numberCollections)
        .fill(null)
        .map((_, index) => (
          <li className="flex flex-col items-center gap-4" key={index}>
            <div className="aspect-square w-full rounded-2xl bg-button lg:min-w-[330px]"></div>
            <div className="flex w-full justify-between gap-4">
              <div className="aspect-square w-1/3 min-w-[100px] rounded-2xl bg-button"></div>
              <div className="aspect-square w-1/3 min-w-[100px] rounded-2xl bg-button"></div>
              <div className="aspect-square w-1/3 min-w-[100px] rounded-2xl bg-button"></div>
            </div>
            <div className="flex w-full flex-col justify-start gap-2 bg-red-400">
              <b>TITLE ABC</b>
              <div className="flex items-center">
                <Avatar>
                  <AvatarImage src="https://github.com/shadcn.png" />
                </Avatar>
                <p>NAME</p>
              </div>
            </div>
          </li>
        ))}
    </ul>
  );
}
