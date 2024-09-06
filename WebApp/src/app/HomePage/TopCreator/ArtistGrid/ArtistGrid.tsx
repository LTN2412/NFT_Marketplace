import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
export default function ArtistGrid() {
  return (
    <div className="grid grid-cols-1 gap-4 lg:order-3 lg:col-span-2 lg:grid-cols-4">
      {Array(4)
        .fill(0)
        .map((_, index) => (
          <div
            key={index}
            className="grid w-full grid-cols-3 items-center rounded-2xl bg-background2 px-8 py-5 md:grid-cols-4 lg:aspect-square lg:grid-cols-1 lg:px-8"
          >
            <Avatar className="w-14 md:w-20 lg:w-24 lg:justify-self-center">
              <AvatarImage
                src="https://github.com/shadcn.png"
                className="rounded-full"
              />
            </Avatar>
            <p className="font-bold lg:justify-self-center">LTN</p>
            <p className="max-md:hidden">
              NFT Solds:<b> 999</b>
            </p>
            <p>
              Volumne:<b> 9999</b>
            </p>
          </div>
        ))}
    </div>
  );
}
