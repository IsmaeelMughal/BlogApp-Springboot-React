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
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";

function SuggestionsOnUserPosts() {
    const navigate = useNavigate();
    const [selectedSuggestionId, setSelectedSuggestionId] = useState();

    const handleDeleteSuggestion = async () => {
        try {
            const res = await myAxios.delete(
                `${BASE_URL}/post/suggestion/delete/${selectedSuggestionId}`,
                {
                    headers: {
                        Authorization: `Bearer ${JSON.parse(
                            localStorage.getItem("token")
                        )}`,
                    },
                }
            );
            if (res.status === 200) {
                var newRows = rows.filter((row) => {
                    if (row.id === selectedSuggestionId) {
                        return false;
                    }
                    return true;
                });
                setRows(newRows);
                toast(res.data.message);
            } else {
                toast(res.data.message);
            }
        } catch (err) {
            toast("Failed To Delete Suggestion");
        }
    };

    const [rows, setRows] = useState([]);
    useEffect(() => {
        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/post/suggestion/userSuggestions`,
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
                    if (res.data.data.length === 0) {
                        toast("No Suggestions By You!!!");
                    } else {
                        toast(res.data.message);
                    }
                } else {
                    toast(res.data.message);
                }
            } catch (err) {
                toast("Failed To Fetch Your Suggestions on Others Posts!!!");
            }
        }
        fetchData();
    }, []);

    return (
        <div className="my-5">
            <Typography variant="h3" gutterBottom>
                Your Suggestions on Others Posts
            </Typography>
            <ToastContainer />
            <TableContainer component={Paper} className="my-5">
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                <h5>Blog Title</h5>
                            </TableCell>

                            <TableCell align="right">
                                <h5>Suggestion</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Reply</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Status</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Action</h5>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow
                                key={row.id}
                                sx={{
                                    "&:last-child td, &:last-child th": {
                                        border: 0,
                                    },
                                }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.onPostTitle}
                                </TableCell>

                                <TableCell align="right">
                                    {row.suggestion}
                                </TableCell>
                                <TableCell align="right">{row.reply}</TableCell>
                                <TableCell align="right">
                                    <span
                                        className={`badge ${
                                            row.status === "PENDING"
                                                ? "bg-warning"
                                                : row.status === "REPLIED"
                                                ? "bg-secondary"
                                                : row.status === "REJECTED"
                                                ? "bg-danger"
                                                : ""
                                        }`}
                                    >
                                        {row.status}
                                    </span>
                                </TableCell>
                                <TableCell align="right">
                                    <div>
                                        <IconButton
                                            color="primary"
                                            onClick={() => {
                                                navigate(
                                                    `/user/post/${row.onPostId}`
                                                );
                                            }}
                                        >
                                            <Visibility />
                                        </IconButton>

                                        <IconButton
                                            color="error"
                                            onClick={() => {
                                                setSelectedSuggestionId(row.id);
                                                handleDeleteSuggestion();
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

export default SuggestionsOnUserPosts;
