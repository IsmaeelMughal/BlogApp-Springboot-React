import React, { useState, useEffect } from "react";
import Comment from "./Comment";
import ReactQuill from "react-quill";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";

function isQuillEmpty(value) {
    if (
        value.replace(/<(.|\n)*?>/g, "").trim().length === 0 &&
        !value.includes("<img")
    ) {
        return true;
    }
    return false;
}

function CommentSection({ postId }) {
    const [value, setValue] = useState("");
    const [currentUser, setCurrentUser] = useState({});

    const [listOfComments, setListOfComments] = useState([]);

    const handleSubmitComment = async () => {
        if (isQuillEmpty(value)) {
            toast("Comment Cannot Be Empty!!!");
        } else {
            if (value.length > 990) {
                toast("Comment is too long!!!");
            } else {
                try {
                    const res = await myAxios.post(
                        `${BASE_URL}/post/addComment`,
                        {
                            postId: postId,
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
                        var newList = listOfComments.concat(res.data.data);
                        setListOfComments(newList);
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

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/post/comments/${postId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${JSON.parse(
                                localStorage.getItem("token")
                            )}`,
                        },
                    }
                );
                if (res.status === 200) {
                    setListOfComments(res.data.data);
                    setCurrentUser(res.data.currentUser);
                } else {
                    toast("Failed To Fetch Comments");
                }
            } catch (err) {
                toast("Failed To Fetch Comments");
            }
        }
        fetchData();
    }, []);

    return (
        <div className="container">
            <ToastContainer />

            <section className="gradient-custom">
                <div className="container my-5 py-5">
                    <div className="row d-flex justify-content-center">
                        <div className="col-md-12 col-lg-10 col-xl-8">
                            <div className="card">
                                <div className="card-body p-4">
                                    <h1 class="display-4 text-center mb-5">
                                        Comments
                                    </h1>
                                    <div className="row">
                                        <div className="col">
                                            {listOfComments &&
                                            listOfComments.length > 0
                                                ? listOfComments.map(
                                                      (comment) => {
                                                          return (
                                                              <Comment
                                                                  commentData={
                                                                      comment
                                                                  }
                                                                  currentUser={
                                                                      currentUser
                                                                  }
                                                                  postId={
                                                                      postId
                                                                  }
                                                                  listOfComments={
                                                                      listOfComments
                                                                  }
                                                                  setListOfComments={
                                                                      setListOfComments
                                                                  }
                                                                  key={
                                                                      comment.id
                                                                  }
                                                              />
                                                          );
                                                      }
                                                  )
                                                : ""}
                                        </div>
                                    </div>
                                </div>

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
                                        onClick={handleSubmitComment}
                                    >
                                        Comment
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default CommentSection;
