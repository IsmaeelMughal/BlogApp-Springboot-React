import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import parse from "html-react-parser";
import { IconButton } from "@mui/material";
import { ThumbUp } from "@mui/icons-material";
import FlagIcon from "@mui/icons-material/Flag";
import CommentSection from "../../components/Comments/CommentSection";
import DeleteIcon from "@mui/icons-material/Delete";
import { useNavigate } from "react-router-dom";
import SuggestionBox from "../../components/SuggestionBox";
import EditIcon from "@mui/icons-material/Edit";

function SinglePost() {
    const navigate = useNavigate();
    const [currentUser, setCurrentUser] = useState({});
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
                toast(res.data.message);
            } else {
                toast(res.data.message);
            }
        } catch (err) {
            toast("Canot Like This Post!!");
        }
    };

    const handleUserPostDelete = async () => {
        try {
            const res = await myAxios.delete(
                `${BASE_URL}/user/post/delete/${postId}`,
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            if (res.status === 200) {
                navigate("/user/mypost");
            } else {
                toast("Failed To Delete Post");
            }
        } catch (err) {
            toast("Failed To Delete Post");
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
                toast(res.data.message);
            } else {
                toast(res.data.message);
            }
        } catch (err) {
            toast("Cannot Report This post!!!");
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
                if (res.status === 200) {
                    setCurrentUser(res.data.currentUser);
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
                        className="masthead"
                        style={{
                            backgroundImage: `url(${postData.image})`,
                        }}
                    >
                        <div className="container position-relative px-4 px-lg-5">
                            <div className="row gx-4 gx-lg-5 justify-content-center">
                                <div className="col-md-10 col-lg-8 col-xl-7">
                                    <div className="post-heading">
                                        <h1>{postData.title}</h1>

                                        <span className="meta">
                                            Posted by {postData.postedBy.name}{" "}
                                            on {postData.date}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </header>
                    {/* <!-- Post Content--> */}
                    <article className="mb-4">
                        <div className="container px-4 px-lg-5">
                            <div className="row gx-4 gx-lg-5 justify-content-center">
                                <div className="col-md-10 col-lg-8 col-xl-7">
                                    {parse(postData.content)}
                                </div>
                            </div>
                        </div>
                    </article>

                    <div className="d-flex my-5 justify-content-center align-items-center">
                        <IconButton onClick={handleLikeClick} color="primary">
                            LIKE <ThumbUp />
                        </IconButton>

                        {currentUser.id !== postData.postedBy.id ||
                        currentUser.role === "ROLE_ADMIN" ? (
                            <IconButton
                                onClick={handleReportClick}
                                color="error"
                            >
                                REPORT
                                <FlagIcon />
                            </IconButton>
                        ) : (
                            ""
                        )}

                        {currentUser.id === postData.postedBy.id ? (
                            <IconButton
                                color="info"
                                onClick={() => {
                                    navigate(`/user/editPost/${postData.id}`);
                                }}
                            >
                                EDIT
                                <EditIcon />
                            </IconButton>
                        ) : (
                            ""
                        )}

                        {currentUser.id === postData.postedBy.id ||
                        currentUser.role === "ROLE_ADMIN" ? (
                            <span>
                                <IconButton
                                    color="secondary"
                                    onClick={handleUserPostDelete}
                                >
                                    DELETE
                                    <DeleteIcon />
                                </IconButton>
                            </span>
                        ) : (
                            ""
                        )}
                    </div>
                    <hr className="my-4" />
                    {currentUser.id !== postData.postedBy.id ? (
                        <SuggestionBox postId={postId} />
                    ) : (
                        ""
                    )}
                    <CommentSection postId={postId} />
                </div>
            ) : (
                ""
            )}
        </div>
    );
}

export default SinglePost;
