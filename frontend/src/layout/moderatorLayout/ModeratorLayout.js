import React, { useEffect } from "react";
import ModeratorHeader from "./ModeratorHeader";
import { Outlet, useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";

function ModeratorLayout() {
    const navigate = useNavigate();
    useEffect(() => {
        // const token = JSON.parse(localStorage.getItem("token"));
        // const role = JSON.parse(localStorage.getItem("role"));
        // if (!token || !role || token === "" || role !== "ROLE_MODERATOR") {
        //     navigate("/");
        // } else {
        //     async function checkAuthority() {
        //         try {
        //             const res = await myAxios.get(
        //                 `${BASE_URL}/user/getDetails`,
        //                 {
        //                     headers: {
        //                         Authorization: `Bearer ${JSON.parse(
        //                             localStorage.getItem("token")
        //                         )}`,
        //                     },
        //                 }
        //             );
        //             if (res.status === 200) {
        //                 if (
        //                     res.data.status === "OK" &&
        //                     res.data.data.role !== "ROLE_MODERATOR"
        //                 ) {
        //                     navigate("/");
        //                 } else if (res.data.status !== "OK") {
        //                     navigate("/");
        //                 }
        //             } else {
        //                 navigate("/");
        //             }
        //         } catch (err) {
        //             navigate("/");
        //         }
        //     }
        //     checkAuthority();
        // }
    }, []);
    return (
        <div>
            <ModeratorHeader />
            <Outlet />
        </div>
    );
}

export default ModeratorLayout;
