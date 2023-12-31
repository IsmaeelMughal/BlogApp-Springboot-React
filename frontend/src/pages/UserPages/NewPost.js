import React, { useState, useEffect } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";

function isQuillEmpty(value) {
    if (
        value.replace(/<(.|\n)*?>/g, "").trim().length === 0 &&
        !value.includes("<img")
    ) {
        return true;
    }
    return false;
}

function NewPost() {
    const [value, setValue] = useState("");
    const [image, setImage] = useState(null);
    const navigate = useNavigate();

    const handleSubmitPost = async (event) => {
        event.preventDefault();
        if (isQuillEmpty(value)) {
            toast("Blog Cannot be Empty!!!");
        } else {
            try {
                const formData = new FormData();
                formData.append("image", image);
                formData.append("title", event.target.title.value);
                formData.append("content", value);

                const res = await myAxios.post(
                    `${BASE_URL}/user/addPost`,
                    formData,
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
                    toast("Post Added Successfully!!!");
                } else {
                    toast("Failed To add Post");
                }
            } catch (err) {
                toast("Failed To add Post");
            }
        }
    };

    const handleUploadClick = (event) => {
        setImage(event.target.files[0]);
    };

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
    }, []);

    return (
        <div className="container my-5">
            <ToastContainer />
            <Typography variant="h3" gutterBottom>
                Add New Post
            </Typography>
            <form onSubmit={handleSubmitPost} encType="multipart/form-data">
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">
                        Title
                    </label>
                    <input
                        type="tect"
                        className="form-control"
                        id="title"
                        name="title"
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

                <div className="form-group">
                    <label htmlFor="image">Select image:</label>
                    <input
                        type="file"
                        id="image"
                        name="image"
                        accept="image/*"
                        onChange={handleUploadClick}
                        required
                    />
                </div>

                <button type="submit" className="btn btn-primary">
                    Submit
                </button>
            </form>
        </div>
    );
}

export default NewPost;
