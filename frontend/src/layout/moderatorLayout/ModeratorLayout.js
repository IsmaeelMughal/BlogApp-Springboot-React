import React from "react";
import ModeratorHeader from "./ModeratorHeader";
import { Outlet } from "react-router-dom";

function ModeratorLayout() {
    return (
        <div>
            <ModeratorHeader />
            <Outlet />
        </div>
    );
}

export default ModeratorLayout;
