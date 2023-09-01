import React, { useState, useEffect } from "react";
import PostCard from "./PostCard";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";

function UserPosts() {
    const [listOfPosts, setListOfPosts] = useState(null);
    useEffect(() => {
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
                        {listOfPosts && listOfPosts.length > 0
                            ? listOfPosts.map((post) => {
                                  return (
                                      <PostCard postData={post} key={post.id} />
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
