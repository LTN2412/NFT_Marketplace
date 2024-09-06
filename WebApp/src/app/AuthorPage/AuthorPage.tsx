import { useLocation } from "react-router-dom";

import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

import Author from "./Author/Author";
import TagBar from "./TagBar/TagBar";

export default function AuthorPage() {
  const location = useLocation().pathname;
  const authorId = location.split("/").pop() || "";
  return (
    <div className="bg-background2 pt-[70px] lg:pt-[100px]">
      <Navbar />
      <Author authorId={authorId} />
      <TagBar authorId={authorId} />
      <Footer />
    </div>
  );
}
