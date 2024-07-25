import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";
import HeadLine from "./HeadLine/HeadLine";
import FormAccount from "./FormAccount/FormAccount";

export default function SignUpPage() {
  return (
    <div>
      <Navbar />
      <HeadLine />
      <FormAccount />
      <Footer />
    </div>
  );
}
