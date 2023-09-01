import React from "react";
import AdminHeader from "./AdminHeader";
import { Outlet } from "react-router-dom";

function AdminLayout() {
    return (
        <div>
            <AdminHeader />
            <Outlet />
        </div>
    );
}

export default AdminLayout;
