import AssetDetailPage from "@/app/AssetDetailPage/AssetDetailPage";
import AuthorPage from "@/app/AuthorPage/AuthorPage";
import HomePage from "@/app/HomePage/HomePage";
import MarketPage from "@/app/MarketPage/MarketPage";
import TopCreatorPage from "@/app/TopCreatorPage/TopCreatorPage";
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
  {
    path: "/asset",
    element: <AssetDetailPage />,
  },
  {
    path: "/author",
    element: <AuthorPage />,
  },
  {
    path: "/topcreator",
    element: <TopCreatorPage />,
  },
]);

export default router;
