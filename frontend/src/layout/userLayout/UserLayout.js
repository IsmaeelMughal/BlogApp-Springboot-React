import React from "react";
import UserHeader from "./UserHeader";
import { Outlet } from "react-router-dom";

function UserLayout() {
    return (
        <div>
            <UserHeader />
            <Outlet />
        </div>
    );
}

export default UserLayout;
