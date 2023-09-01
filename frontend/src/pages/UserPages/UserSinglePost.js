import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import parse from "html-react-parser";
import { IconButton } from "@mui/material";
import { ThumbUp } from "@mui/icons-material";
import FlagIcon from "@mui/icons-material/Flag";

function SinglePost() {
    const [likeResponse, setLikeResponse] = useState({ message: "" });
    const handleLikeClick = async () => {
        try {
            const res = await myAxios.post(
                `${BASE_URL}/user/post/like/${postData.id}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            if (res.status === 200) {
                setLikeResponse(res.data);
                toast(res.data.message);
            } else {
                toast(likeResponse.message);
            }
        } catch (err) {
            toast(likeResponse.message);
        }
    };

    const handleReportClick = async () => {
        try {
            const res = await myAxios.post(
                `${BASE_URL}/user/post/report/${postData.id}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            if (res.status === 200) {
                setLikeResponse(res.data);
                toast(res.data.message);
            } else {
                toast(likeResponse.message);
            }
        } catch (err) {
            toast(likeResponse.message);
        }
    };

    let { postId } = useParams();
    const [postData, setPostData] = useState(null);

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
            {/* <!-- Page Header--> */}
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

                    <div className="d-flex my-5 justify-content-center align-items-center">
                        <IconButton onClick={handleLikeClick} color="primary">
                            LIKE <ThumbUp />
                        </IconButton>

                        <IconButton onClick={handleReportClick} color="error">
                            REPORT
                            <FlagIcon />
                        </IconButton>
                    </div>
                </div>
            ) : (
                toast("Failed To Fetch Post")
            )}
        </div>
    );
}

export default SinglePost;
