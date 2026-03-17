import AdminLayout from "../layouts/AdminLayout"; "../layouts/AdminLayout";
import UserPage from "@/features/admin/users/pages/UserPage";

const adminRoutes = [
    {
        path: "/admin",
        element: <AdminLayout />,
        children: [
            { path: "user", element: <UserPage /> },
        ]
    }
]
export default adminRoutes