import React from "react";
import "../../styles/dashboards.css";
import { useState, useEffect } from "react";
import "../../styles/Admin.css";
import { useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import TotalUsers from "../../images/total-users.png";
import { ToastContainer, toast } from "react-toastify";

function AdminHome() {
    const navigate = useNavigate();
    const [details, setDetails] = useState("");
    useEffect(() => {
        const token = JSON.parse(localStorage.getItem("token"));
        const role = JSON.parse(localStorage.getItem("role"));
        if (!token || !role || token === "" || role !== "ROLE_ADMIN") {
            navigate("/");
        } else {
            async function checkAuthority() {
                try {
                    const res = await myAxios.get(
                        `${BASE_URL}/user/getDetails`,
                        {
                            headers: {
                                Authorization: `Bearer ${JSON.parse(
                                    localStorage.getItem("token")
                                )}`,
                            },
                        }
                    );
                    if (res.status === 200) {
                        if (
                            res.data.status === "OK" &&
                            res.data.data.role !== "ROLE_ADMIN"
                        ) {
                            navigate("/");
                        } else if (res.data.status !== "OK") {
                            navigate("/");
                        }
                    } else {
                        navigate("/");
                    }
                } catch (err) {
                    navigate("/");
                }
            }
            checkAuthority();
        }

        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/admin/getDetailsOfApp`,
                    {
                        headers: {
                            Authorization: `Bearer ${JSON.parse(
                                localStorage.getItem("token")
                            )}`,
                        },
                    }
                );
                if (res.status === 200) {
                    console.log(res.data.data);
                    setDetails(res.data.data);
                    toast(res.data.message);
                }
            } catch (err) {
                toast("Failed To Fetch Moderators");
            }
        }
        fetchData();
    }, []);

    return (
        <div class="main d-flex justify-content-center align-items-center vh-100">
            <ToastContainer />

            <div class="box-container">
                <div class="box box1">
                    <div class="text">
                        <h2 class="topic-heading">{details.totalUsers}</h2>
                        <h2 class="topic">Total Users</h2>
                    </div>
                    <img src={TotalUsers} alt="Views" />
                </div>
                <div class="box box2">
                    <div class="text">
                        <h2 class="topic-heading">{details.totalLikes}</h2>
                        <h2 class="topic">Total Likes</h2>
                    </div>
                    <img
                        src="https://media.geeksforgeeks.org/wp-content/uploads/20221210185030/14.png"
                        alt="likes"
                    />
                </div>
                <div class="box box3">
                    <div class="text">
                        <h2 class="topic-heading">{details.totalComments}</h2>
                        <h2 class="topic">Total Comments</h2>
                    </div>
                    <img
                        src="https://media.geeksforgeeks.org/wp-content/uploads/20221210184645/Untitled-design-(32).png"
                        alt="comments"
                    />
                </div>
                <div class="box box4">
                    <div class="text">
                        <h2 class="topic-heading">{details.totalPosts}</h2>
                        <h2 class="topic">Total Posts</h2>
                    </div>
                    <img
                        src="https://media.geeksforgeeks.org/wp-content/uploads/20221210185029/13.png"
                        alt="published"
                    />
                </div>
            </div>
        </div>
    );
}

export default AdminHome;
