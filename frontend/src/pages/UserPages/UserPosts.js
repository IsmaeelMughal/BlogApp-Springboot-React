import React, { useState, useEffect } from "react";
import PostCard from "./PostCard";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function UserPosts() {
    const [listOfPosts, setListOfPosts] = useState(null);
    const [currentUser, setCurrentUser] = useState();
    const navigate = useNavigate();
    useEffect(() => {
        const token = JSON.parse(localStorage.getItem("token"));
        const role = JSON.parse(localStorage.getItem("role"));
        if (!token || !role || token === "" || role !== "ROLE_USER") {
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
                            res.data.data.role !== "ROLE_USER"
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
                const res = await myAxios.get(`${BASE_URL}/user/posts`, {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                });
                console.log(res);
                if (res.status === 200) {
                    console.log(res.data.data);
                    setListOfPosts(res.data.data);
                    setCurrentUser(res.data.currentUser);
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

            <div className="container px-4 px-lg-5">
                <div className="row gx-4 gx-lg-5 justify-content-center">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        {listOfPosts && listOfPosts.length > 0
                            ? listOfPosts.map((post) => {
                                  return (
                                      <PostCard
                                          postData={post}
                                          currentUser={currentUser}
                                          key={post.id}
                                      />
                                  );
                              })
                            : "NO POST FOUND"}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UserPosts;
