import { useParams } from "react-router-dom";
import React, { useState, useEffect } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import Typography from "@mui/material/Typography";

function isQuillEmpty(value) {
    if (
        value.replace(/<(.|\n)*?>/g, "").trim().length === 0 &&
        !value.includes("<img")
    ) {
        return true;
    }
    return false;
}

function EditPost() {
    let { postId } = useParams();

    const [value, setValue] = useState("");
    const [title, setTitle] = useState("");

    const handleSubmitPost = async (event) => {
        event.preventDefault();
        if (isQuillEmpty(value)) {
            toast("Blog Cannot be Empty!!!");
        } else {
            try {
                const res = await myAxios.patch(
                    `${BASE_URL}/user/editPost/${postId}`,
                    {
                        title: title,
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
                } else {
                    toast("Failed To Edit Post");
                }
            } catch (err) {
                toast("Failed To Edit Post");
            }
        }
    };

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
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
                if (res.status === 200) {
                    setValue(res.data.data.content);
                    setTitle(res.data.data.title);
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
        <div className="container my-5">
            <ToastContainer />
            <Typography variant="h3" gutterBottom>
                Edit Post
            </Typography>
            <form onSubmit={handleSubmitPost}>
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">
                        Title
                    </label>
                    <input
                        type="tect"
                        className="form-control"
                        id="title"
                        name="title"
                        value={title}
                        onChange={handleTitleChange}
                        aria-describedby="title"
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="content" className="form-label">
                        Content
                    </label>
                    <ReactQuill
                        theme="snow"
                        value={value}
                        onChange={setValue}
                    />
                </div>

                <button type="submit" className="btn btn-primary">
                    Edit
                </button>
            </form>
        </div>
    );
}

export default EditPost;
