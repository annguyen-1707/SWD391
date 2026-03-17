import HomePage from "@/features/user/components/HomePage";
import React from "react";
import PublicLayouts from "../layouts/PublicLayouts";
import UserLogin from "../guards/UserLogin";
import Register from "../guards/Register";
import Logout from "../guards/Logout";

const publicRoutes = [
  { path: "/login", element: <UserLogin /> },
  { path: "/register", element: <Register /> },
  { path: "/logout", element: <Logout /> },
  {
    path: "/", element: <PublicLayouts />,
    children: [
      { index: true, element: <HomePage /> },
    ]
  }
]


export default publicRoutes