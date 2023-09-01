import { React, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import parse from "html-react-parser";

function ModeratorSinglePostDetails() {
    let { postId } = useParams();
    const [postData, setPostData] = useState(null);

    const navigate = useNavigate();

    const handleApprovePostModerator = async () => {
        try {
            const res = await myAxios.post(
                `${BASE_URL}/moderator/post/approve/${postData.id}`,
                {},
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
                navigate("/moderator");
            } else {
                toast("Failed To Approve Post");
            }
        } catch (err) {
            toast("Failed To Approve Post");
        }
    };

    const handleDeletePostModerator = async () => {
        console.log("DELETE" + postData.id);
        try {
            const res = await myAxios.delete(
                `${BASE_URL}/moderator/post/delete/${postData.id}`,
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
                navigate("/moderator");
            } else {
                toast("Failed To Delete Post");
            }
        } catch (err) {
            toast("Failed To Delete Post");
        }
    };

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await myAxios.get(`${BASE_URL}/post/${postId}`, {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                });
                console.log(res);
                if (res.status === 200) {
                    console.log(res.data.data);
                    setPostData(res.data.data);
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
            {postData != null ? (
                <div>
                    <header
                        class="masthead"
                        style={{
                            "background-image": `url(${postData.image})`,
                        }}
                    >
                        <div class="container position-relative px-4 px-lg-5">
                            <div class="row gx-4 gx-lg-5 justify-content-center">
                                <div class="col-md-10 col-lg-8 col-xl-7">
                                    <div class="post-heading">
                                        <h1>{postData.title}</h1>

                                        <span class="meta">
                                            Posted by {postData.postedBy.name}{" "}
                                            on {postData.date}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </header>
                    {/* <!-- Post Content--> */}
                    <article class="mb-4">
                        <div class="container px-4 px-lg-5">
                            <div class="row gx-4 gx-lg-5 justify-content-center">
                                <div class="col-md-10 col-lg-8 col-xl-7">
                                    {parse(postData.content)}
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            ) : (
                toast("Failed To Fetch Post")
            )}
            <hr />
            <div className="d-flex my-5 justify-content-center align-items-center">
                <button
                    className="btn btn-outline-primary mx-5"
                    onClick={handleApprovePostModerator}
                >
                    Approve
                </button>
                <button
                    className="btn btn-outline-danger mx-5"
                    onClick={handleDeletePostModerator}
                >
                    Delete
                </button>
            </div>
        </div>
    );
}

export default ModeratorSinglePostDetails;
