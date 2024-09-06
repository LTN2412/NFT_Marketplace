import { createBrowserRouter } from "react-router-dom";

import AssetDetailPage from "@/app/AssetDetailPage/AssetDetailPage";
import AuthorPage from "@/app/AuthorPage/AuthorPage";
import CartPage from "@/app/CartPage/CartPage";
import FormAccountPage from "@/app/FormAccountPage/FormAccount";
import SignIn from "@/app/FormAccountPage/SignIn/SignIn";
import SignUp from "@/app/FormAccountPage/SignUp/SignUp";
import Info from "@/app/FormAccountPage/UpdateInfo/UpdateInfo";
import HomePage from "@/app/HomePage/HomePage";
import MarketPage from "@/app/MarketPage/MarketPage";
import ProfilePage from "@/app/ProfilePage/ProfilePage";
import RankingPage from "@/app/RankingPage/RankingPage";
import WalletPage from "@/app/WalletPage/WalletPage";

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
    path: "/cart",
    element: <CartPage />,
  },
  {
    path: "/ranking",
    element: <RankingPage />,
  },
  {
    element: <FormAccountPage />,
    children: [
      { path: "/signup", element: <SignUp /> },
      { path: "/signin", element: <SignIn /> },
      { path: "/info", element: <Info /> },
    ],
  },
  {
    path: "/profile",
    element: <ProfilePage />,
  },
  {
    path: "/wallet",
    element: <WalletPage />,
  },
]);

export default router;
