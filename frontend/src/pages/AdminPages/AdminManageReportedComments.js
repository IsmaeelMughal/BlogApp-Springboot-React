import * as React from "react";
import { useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { ToastContainer, toast } from "react-toastify";
import { IconButton } from "@mui/material";
import { Delete, Visibility } from "@mui/icons-material";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import parse from "html-react-parser";
import { useNavigate } from "react-router-dom";

function AdminManageReportedComments() {
    const [rows, setRows] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/admin/comment/reportedComments`,
                    {
                        headers: {
                            Authorization: `Bearer ${JSON.parse(
                                localStorage.getItem("token")
                            )}`,
                        },
                    }
                );
                if (res.status === 200) {
                    setRows(res.data.data);
                    toast("Total Comments Count: " + res.data.data.length);
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
            <TableContainer component={Paper} className="my-5">
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                <h5>Comment</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Commented By</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Likes</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Reports</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Action</h5>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow
                                key={row.commentId}
                                sx={{
                                    "&:last-child td, &:last-child th": {
                                        border: 0,
                                    },
                                }}
                            >
                                <TableCell component="th" scope="row">
                                    {parse(row.comment)}
                                </TableCell>
                                <TableCell align="right">
                                    {row.commentedBy.name}
                                </TableCell>
                                <TableCell align="right">
                                    {row.numberOfLikes}
                                </TableCell>
                                <TableCell align="right">
                                    {row.numberOfReports}
                                </TableCell>
                                <TableCell align="right">
                                    <div>
                                        <IconButton
                                            color="primary"
                                            onClick={() => {
                                                navigate(
                                                    `/admin/post/${row.postId}`
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
                                                            `${BASE_URL}/admin/deleteUser/${row.id}`,
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
                                                        console.log(
                                                            res.data.data
                                                        );

                                                        const newRows =
                                                            rows.filter(
                                                                (user) => {
                                                                    if (
                                                                        user.id !==
                                                                        row.id
                                                                    ) {
                                                                        return true;
                                                                    }
                                                                    return false;
                                                                }
                                                            );
                                                        setRows(newRows);

                                                        toast(
                                                            "User Deleted Successfully!!!"
                                                        );
                                                    } else {
                                                        toast(
                                                            "Failed To Delete User"
                                                        );
                                                    }
                                                } catch (err) {
                                                    toast(
                                                        "Failed To Delete User"
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

export default AdminManageReportedComments;
