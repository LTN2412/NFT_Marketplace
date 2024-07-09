import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
import { useState } from "react";
export default function ArtistGrid() {
  const [numberArtists, setNumberArtists] = useState(
    window.innerWidth >= 1024 ? 16 : 4,
  );
  window.addEventListener("resize", () =>
    window.innerWidth >= 1024 ? setNumberArtists(16) : setNumberArtists(4),
  );
  return (
    <div className="grid grid-cols-1 gap-4 lg:order-3 lg:col-span-2 lg:grid-cols-4">
      {Array(numberArtists)
        .fill(null)
        .map((_, index) => (
          <div
            key={index}
            className="grid w-full grid-cols-2 items-center rounded-2xl bg-background2 px-8 py-5 lg:aspect-square lg:grid-cols-1 lg:px-8"
          >
            <Avatar className="place-self-center">
              <AvatarImage
                src="https://github.com/shadcn.png"
                className="w-16 rounded-full"
              />
            </Avatar>
            <div className="flex items-center gap-2 place-self-center whitespace-nowrap text-sm">
              <b>LTN</b>
              <p>
                TOTAL_SALE:<b> 9999</b>
              </p>
            </div>
          </div>
        ))}
    </div>
  );
}
