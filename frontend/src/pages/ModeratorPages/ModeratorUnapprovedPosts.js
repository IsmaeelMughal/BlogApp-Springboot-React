import React, { useState, useEffect } from "react";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import ModeratorPostCard from "./ModeratorPostCard";
import { useNavigate } from "react-router-dom";

function ModeratorUnapprovedPosts() {
    const [listOfPosts, setListOfPosts] = useState(null);
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
        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/moderator/posts/unapproved`,
                    {
                        headers: {
                            Authorization: `Bearer ${JSON.parse(
                                localStorage.getItem("token")
                            )}`,
                        },
                    }
                );
                console.log(res);
                if (res.status === 200) {
                    console.log(res.data.data);
                    setListOfPosts(res.data.data);
                    if (res.data.data.length === 0) {
                        toast("No Post to Approve!!!");
                    } else {
                        toast("All unApproved Posts!!!");
                    }
                } else {
                    toast("Failed To Fetch Post");
                }
            } catch (err) {
                toast("Failed To Fetch Post");
            }
        }
        fetchData();
    }, []);
    return (
        <div>
            <ToastContainer />

            <div class="container px-4 px-lg-5">
                <div class="row gx-4 gx-lg-5 justify-content-center">
                    <div class="col-md-10 col-lg-8 col-xl-7">
                        {listOfPosts && listOfPosts.length > 0 ? (
                            listOfPosts.map((post) => {
                                return (
                                    <ModeratorPostCard
                                        postData={post}
                                        key={post.id}
                                    />
                                );
                            })
                        ) : (
                            <h1 className="my-5 container d-flex align-item-center justify-content-center">
                                NO POST FOUND
                            </h1>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ModeratorUnapprovedPosts;
