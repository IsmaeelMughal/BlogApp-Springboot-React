import * as React from "react";
import { useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import { IconButton } from "@mui/material";
import { Visibility, Delete } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";

function AdminReportedPosts() {
    const [rows, setRows] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem("token"));
        const role = JSON.parse(localStorage.getItem("role"));
        if (!token || !role || token === "" || role !== "ROLE_ADMIN") {
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
                            res.data.data.role !== "ROLE_ADMIN"
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
                const res = await myAxios.get(
                    `${BASE_URL}/moderator/post/getReportedPosts`,
                    {
                        headers: {
                            Authorization: `Bearer ${JSON.parse(
                                localStorage.getItem("token")
                            )}`,
                        },
                    }
                );
                if (res.status === 200) {
                    console.log(res.data.data);
                    setRows(res.data.data);
                    if (res.data.data.length === 0) {
                        toast("No Reported Post!!!");
                    } else {
                        toast("All Reported Posts!!!");
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
        <div className="container my-5">
            <ToastContainer />
            <TableContainer component={Paper} className="my-5">
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                <h5>Title</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Posted By</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Date</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Number of Reports</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Number of Likes</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Action</h5>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow
                                key={row.postId}
                                sx={{
                                    "&:last-child td, &:last-child th": {
                                        border: 0,
                                    },
                                }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.title}
                                </TableCell>
                                <TableCell align="right">
                                    {row.postedBy}
                                </TableCell>
                                <TableCell align="right">{row.date}</TableCell>
                                <TableCell align="right">
                                    {row.numberOfReports}
                                </TableCell>
                                <TableCell align="right">
                                    {row.numberOfLikes}
                                </TableCell>
                                <TableCell align="right">
                                    <div>
                                        <IconButton
                                            color="primary"
                                            onClick={() => {
                                                navigate(
                                                    `/admin/post/${row.id}`
                                                );
                                            }}
                                        >
                                            <Visibility />
                                        </IconButton>

                                        <IconButton
                                            color="error"
                                            onClick={async () => {
                                                try {
                                                    const res =
                                                        await myAxios.delete(
                                                            `${BASE_URL}/moderator/post/delete/${row.postId}`,
                                                            {
                                                                headers: {
                                                                    Authorization: `Bearer ${JSON.parse(
                                                                        localStorage.getItem(
                                                                            "token"
                                                                        )
                                                                    )}`,
                                                                },
                                                            }
                                                        );
                                                    if (res.status === 200) {
                                                        const newRows =
                                                            rows.filter(
                                                                (post) => {
                                                                    if (
                                                                        post.postId !==
                                                                        row.postId
                                                                    ) {
                                                                        return true;
                                                                    }
                                                                    return false;
                                                                }
                                                            );
                                                        setRows(newRows);
                                                        toast(
                                                            "Post Deleted Successfully!!!"
                                                        );
                                                    } else {
                                                        toast(
                                                            "Failed To Delete Post"
                                                        );
                                                    }
                                                } catch (err) {
                                                    toast(
                                                        "Failed To Delete Post"
                                                    );
                                                }
                                            }}
                                        >
                                            <Delete />
                                        </IconButton>
                                    </div>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default AdminReportedPosts;
