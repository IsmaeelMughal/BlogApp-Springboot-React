import React, { useState } from "react";
import commentProfileImage from "../../images/comment-profile.png";
import Reply from "./Reply";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import ReportIcon from "@mui/icons-material/Report";
import parse from "html-react-parser";
import ReactQuill from "react-quill";
import IconButton from "@mui/material/IconButton";
import { ToastContainer, toast } from "react-toastify";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import DeleteIcon from "@mui/icons-material/Delete";

function isQuillEmpty(value) {
    if (
        value.replace(/<(.|\n)*?>/g, "").trim().length === 0 &&
        !value.includes("<img")
    ) {
        return true;
    }
    return false;
}

function Comment({
    commentData,
    currentUser,
    postId,
    listOfComments,
    setListOfComments,
}) {
    const [value, setValue] = useState("");

    const handleUserCommentReport = async () => {
        try {
            const res = await myAxios.post(
                `${BASE_URL}/post/comment/report`,
                {
                    postId: postId,
                    commentId: commentData.id,
                },
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            toast(res.data.message);
        } catch (err) {
            toast("Failed To Report!!!");
        }
    };

    const handleUserCommentLike = async () => {
        try {
            const res = await myAxios.post(
                `${BASE_URL}/post/comment/like`,
                {
                    postId: postId,
                    commentId: commentData.id,
                },
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            toast(res.data.message);
        } catch (err) {
            toast("Failed To Comment!!!");
        }
    };

    const handleUserCommentDelete = async () => {
        try {
            const res = await myAxios.delete(
                `${BASE_URL}/post/comment/delete/${commentData.id}`,
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            if (res.status === 200) {
                const newComments = listOfComments.filter((comment) => {
                    if (comment.id !== commentData.id) {
                        return true;
                    }
                    return false;
                });
                setListOfComments(newComments);
                toast(res.data.message);
            } else {
                toast("Failed To Delete Comment");
            }
        } catch (err) {
            toast("Failed To Delete Comment");
        }
    };

    const handleSubmitCommentReply = async () => {
        if (isQuillEmpty(value)) {
            toast("Reply Cannot Be Empty!!!");
        } else {
            if (value.length > 990) {
                toast("Reply is too long!!!");
            } else {
                try {
                    const res = await myAxios.post(
                        `${BASE_URL}/post/comment/addReply`,
                        {
                            commentId: commentData.id,
                            content: value,
                        },
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
                        var newReplies = commentData.replies.concat(
                            res.data.data
                        );
                        commentData.replies = newReplies;
                        setValue("");
                    } else {
                        toast(res.data.message);
                    }
                } catch (err) {
                    toast("Failed To Comment!!!");
                }
            }
        }
    };

    return (
        <div>
            <ToastContainer />

            <div className="d-flex flex-start">
                <img
                    className="rounded-circle shadow-1-strong me-3"
                    src={commentProfileImage}
                    alt="avatar"
                    width="65"
                    height="65"
                />
                <div className="flex-grow-1 flex-shrink-1">
                    <div>
                        <div className="d-flex justify-content-between align-items-center">
                            <span className="mb-1">
                                <span className="font-weight-bold">
                                    {commentData.commentedBy.name}
                                </span>
                                {currentUser.id ===
                                commentData.commentedBy.id ? (
                                    <span className="bg-warning rounded p-1 m-3">
                                        you
                                    </span>
                                ) : (
                                    ""
                                )}
                            </span>
                            <span>
                                <IconButton
                                    color="primary"
                                    onClick={handleUserCommentLike}
                                >
                                    <ThumbUpIcon />
                                </IconButton>

                                {currentUser.id !==
                                    commentData.commentedBy.id ||
                                currentUser.role === "ROLE_ADMIN" ? (
                                    <IconButton
                                        color="error"
                                        onClick={handleUserCommentReport}
                                    >
                                        <ReportIcon />
                                    </IconButton>
                                ) : (
                                    ""
                                )}

                                {currentUser.id ===
                                    commentData.commentedBy.id ||
                                currentUser.role === "ROLE_ADMIN" ? (
                                    <IconButton
                                        color="secondary"
                                        onClick={handleUserCommentDelete}
                                    >
                                        <DeleteIcon />
                                    </IconButton>
                                ) : (
                                    ""
                                )}
                            </span>
                        </div>
                        <span className="small mb-0">
                            {parse(commentData.content)}
                        </span>
                    </div>

                    {commentData.replies && commentData.replies.length > 0
                        ? commentData.replies.map((reply) => {
                              return (
                                  <Reply
                                      replyData={reply}
                                      currentUser={currentUser}
                                      key={reply.id}
                                  />
                              );
                          })
                        : ""}

                    <div>
                        <ReactQuill
                            className="mx-3"
                            theme="snow"
                            value={value}
                            onChange={setValue}
                            modules={{
                                toolbar: [
                                    [
                                        "bold",
                                        "italic",
                                        "underline",
                                        "strike",
                                        "blockquote",
                                    ],

                                    ["link"],
                                ],
                            }}
                            formats={[
                                "bold",
                                "italic",
                                "underline",
                                "strike",
                                "blockquote",
                                "link",
                            ]}
                        />

                        <button
                            className="btn btn-primary my-3 mx-3"
                            onClick={handleSubmitCommentReply}
                        >
                            Reply
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Comment;
