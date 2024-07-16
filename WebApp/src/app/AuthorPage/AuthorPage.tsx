import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import Author from "./Author/Author";
import TagBar from "./TagBar/TagBar";

export default function AuthorPage() {
  return (
    <div className="bg-background2">
      <Navbar />
      <Author
        authorCoverImg="/Cover.png"
        authorAvatar={"/Avatar.png"}
        authorName={"LTN"}
        authorVolumne={250}
        authorSold={300}
        authorFollower={900}
        authorBio={"This is Bio for test abcd12345"}
      />
      <TagBar />
      <Footer />
    </div>
  );
}
