export default function FirstCharName(name: string) {
  return name
    ? name
        .split(" ")
        .map((word) => word.charAt(0))
        .join("")
        .toUpperCase()
    : null;
}
