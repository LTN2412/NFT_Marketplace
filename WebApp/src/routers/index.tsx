import AssetDetailPage from "@/app/AssetDetailPage/AssetDetailPage";
import AuthorPage from "@/app/AuthorPage/AuthorPage";
import HomePage from "@/app/HomePage/HomePage";
import MarketPage from "@/app/MarketPage/MarketPage";
import RankingPage from "@/app/RankingPage/RankingPage";
import SignUpPage from "@/app/SignUpPage/SignUpPage";
import { createBrowserRouter } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/marketplace",
    element: <MarketPage />,
    children: [
      { path: "/marketplace/nfts", element: null },
      { path: "/marketplace/collections", element: null },
    ],
  },
  {
    path: "/asset",
    element: <AssetDetailPage />,
    children: [{ path: "/asset/:id", element: null }],
  },
  {
    path: "/author",
    element: <AuthorPage />,
    children: [{ path: "/author/:id", element: null }],
  },
  {
    path: "/ranking",
    element: <RankingPage />,
  },
  {
    path: "/signup",
    element: <SignUpPage />,
  },
]);

export default router;
