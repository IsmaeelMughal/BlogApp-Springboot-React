import React from "react";
import "../../styles/dashboards.css";
import { useState, useEffect } from "react";
import "../../styles/Admin.css";
import { useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";

function AdminHome() {
    const navigate = useNavigate();
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
    }, []);

    return (
        <div id="wrapper my-5">
            <div id="content-wrapper">
                <div class="main-container">
                    <div class="main">
                        <div class="box-container">
                            <div class="box box1">
                                <div class="text">
                                    <h2 class="topic-heading">60.5k</h2>
                                    <h2 class="topic">Article Views</h2>
                                </div>
                                <img
                                    src="https://media.geeksforgeeks.org/wp-content/uploads/20221210184645/Untitled-design-(31).png"
                                    alt="Views"
                                />
                            </div>
                            <div class="box box2">
                                <div class="text">
                                    <h2 class="topic-heading">150</h2>
                                    <h2 class="topic">Likes</h2>
                                </div>
                                <img
                                    src="https://media.geeksforgeeks.org/wp-content/uploads/20221210185030/14.png"
                                    alt="likes"
                                />
                            </div>
                            <div class="box box3">
                                <div class="text">
                                    <h2 class="topic-heading">320</h2>
                                    <h2 class="topic">Comments</h2>
                                </div>
                                <img
                                    src="https://media.geeksforgeeks.org/wp-content/uploads/20221210184645/Untitled-design-(32).png"
                                    alt="comments"
                                />
                            </div>
                            <div class="box box4">
                                <div class="text">
                                    <h2 class="topic-heading">70</h2>
                                    <h2 class="topic">Published</h2>
                                </div>
                                <img
                                    src="https://media.geeksforgeeks.org/wp-content/uploads/20221210185029/13.png"
                                    alt="published"
                                />
                            </div>
                        </div>
                        {/* <!-- Content Wrapper --> */}
                        <div class="d-flex flex-column my-5">
                            {/* <!-- Main Content --> */}
                            <div id="content">
                                {/* <!-- Begin Page Content --> */}
                                <div class="container-fluid">
                                    {/* <!-- Content Row --> */}
                                    <div class=" d-flex">
                                        {/* <!-- Earnings (Monthly) Card Example --> */}
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card border-left-primary shadow h-100 py-2">
                                                <div class="card-body">
                                                    <div class="row no-gutters align-items-center">
                                                        <div class="col mr-2">
                                                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                                Earnings
                                                                (Monthly)
                                                            </div>
                                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                                $40,000
                                                            </div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        {/* <!-- Earnings (Monthly) Card Example --> */}
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card border-left-success shadow h-100 py-2">
                                                <div class="card-body">
                                                    <div class="row no-gutters align-items-center">
                                                        <div class="col mr-2">
                                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                                Earnings
                                                                (Annual)
                                                            </div>
                                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                                $215,000
                                                            </div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        {/* <!-- Earnings (Monthly) Card Example --> */}
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card border-left-info shadow h-100 py-2">
                                                <div class="card-body">
                                                    <div class="row no-gutters align-items-center">
                                                        <div class="col mr-2">
                                                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                                Tasks
                                                            </div>
                                                            <div class="row no-gutters align-items-center">
                                                                <div class="col-auto">
                                                                    <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                                                                        50%
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        {/* <!-- Pending Requests Card Example --> */}
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card border-left-warning shadow h-100 py-2">
                                                <div class="card-body">
                                                    <div class="row no-gutters align-items-center">
                                                        <div class="col mr-2">
                                                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                Pending Requests
                                                            </div>
                                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                                18
                                                            </div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <i class="fas fa-comments fa-2x text-gray-300"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                {/* <!-- /.container-fluid --> */}
                            </div>
                            {/* <!-- End of Main Content --> */}
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- End of Content Wrapper --> */}
        </div>
    );
}

export default AdminHome;
