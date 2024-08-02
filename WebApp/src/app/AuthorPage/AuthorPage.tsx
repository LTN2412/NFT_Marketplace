import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import Author from "./Author/Author";
import TagBar from "./TagBar/TagBar";
import { useLocation } from "react-router-dom";

export default function AuthorPage() {
  const location = useLocation().pathname;
  const id = location.split("/").pop() || "";
  return (
    <div className="bg-background2">
      <Navbar />
      <Author id={id} />
      <TagBar id={id} />
      <Footer />
    </div>
  );
}
