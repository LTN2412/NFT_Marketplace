import HomePage from "@/app/HomePage/HomePage";
import MarketPage from "@/app/MarketPage/MarketPage";
import { createBrowserRouter } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/marketplace",
    element: <MarketPage />,
  },
]);

export default router;
