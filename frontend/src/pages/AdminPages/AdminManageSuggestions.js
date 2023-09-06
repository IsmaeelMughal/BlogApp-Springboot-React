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
import ReplyIcon from "@mui/icons-material/Reply";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";
import CancelIcon from "@mui/icons-material/Cancel";
import Tooltip from "@mui/material/Tooltip";

function AdminManageSuggestions() {
    const navigate = useNavigate();
    const [rows, setRows] = useState([]);

    const [selectedSuggestionId, setSelectedSuggestionId] = useState();

    const [replyText, setReplyText] = useState("");

    const [openModal, setOpenModal] = useState(false);

    const handleRejectClick = async () => {
        try {
            const res = await myAxios.patch(
                `${BASE_URL}/post/suggestion/reject/${selectedSuggestionId}`,
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
                var newRows = rows.map((row, index) => {
                    if (row.id === selectedSuggestionId) {
                        row.status = "REJECTED";
                    }
                    return row;
                });

                setRows(newRows);
            } else {
                toast(res.data.message);
            }
        } catch (err) {
            toast("Failed To Reject!!!");
        }
    };

    const handleReplyToSuggestionClick = async () => {
        setOpenModal(false);
        if (replyText === null || replyText.trim() === "") {
            toast("Reply Cannot Be Empty!!!");
        } else {
            try {
                const res = await myAxios.patch(
                    `${BASE_URL}/post/suggestion/addReply`,
                    {
                        suggestionId: selectedSuggestionId,
                        reply: replyText,
                    },
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
                    toast(res.data.message);

                    var newRows = rows.map((row, index) => {
                        if (row.id === selectedSuggestionId) {
                            row.status = "REPLIED";
                            row.reply = replyText;
                        }
                        return row;
                    });

                    setRows(newRows);
                } else {
                    toast(res.data.message);
                }
            } catch (err) {
                toast("Failed To Reply!!!");
            }
        }
    };

    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    const handleReplyTextChange = (event) => {
        setReplyText(event.target.value);
    };

    const handleReplyClick = () => {
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

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
                    `${BASE_URL}/admin/getAllSuggestions`,
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
                        toast("No Suggestions in App!!!");
                    } else {
                        toast(res.data.message);
                    }
                } else {
                    toast(res.data.message);
                }
            } catch (err) {
                toast("Failed To Fetch Suggestions!!!");
            }
        }
        fetchData();
    }, []);

    return (
        <div className="my-5 container">
            <Typography variant="h3" gutterBottom>
                All Suggestions On Posts
            </Typography>
            <ToastContainer />
            <TableContainer component={Paper} className="my-5">
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                <h5>Blog Title</h5>
                            </TableCell>
                            <TableCell>
                                <h5>Suggested By</h5>
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
                                    {row.suggestedBy.name}
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
                                        <Tooltip
                                            title={isHovered ? "View Post" : ""}
                                        >
                                            <IconButton
                                                color="primary"
                                                onClick={() => {
                                                    navigate(
                                                        `/admin/post/${row.onPostId}`
                                                    );
                                                }}
                                                onMouseEnter={handleMouseEnter}
                                                onMouseLeave={handleMouseLeave}
                                            >
                                                <Visibility />
                                            </IconButton>
                                        </Tooltip>
                                        <Tooltip
                                            title={isHovered ? "Reply" : ""}
                                        >
                                            <IconButton
                                                color="secondary"
                                                onClick={() => {
                                                    setSelectedSuggestionId(
                                                        row.id
                                                    );
                                                    handleReplyClick();
                                                }}
                                                onMouseEnter={handleMouseEnter}
                                                onMouseLeave={handleMouseLeave}
                                            >
                                                <ReplyIcon />
                                            </IconButton>
                                        </Tooltip>
                                        <Tooltip
                                            title={isHovered ? "Reject" : ""}
                                        >
                                            <IconButton
                                                color="error"
                                                onClick={() => {
                                                    setSelectedSuggestionId(
                                                        row.id
                                                    );
                                                    handleRejectClick();
                                                }}
                                                onMouseEnter={handleMouseEnter}
                                                onMouseLeave={handleMouseLeave}
                                            >
                                                <CancelIcon />
                                            </IconButton>
                                        </Tooltip>
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
            <Dialog open={openModal} onClose={handleCloseModal}>
                <DialogContent>
                    <TextField
                        label="Reply"
                        multiline
                        rows={4}
                        variant="outlined"
                        fullWidth
                        value={replyText}
                        onChange={handleReplyTextChange}
                    />
                    <Button onClick={handleCloseModal} color="primary">
                        Cancel
                    </Button>
                    <Button
                        onClick={handleReplyToSuggestionClick}
                        color="primary"
                    >
                        Reply
                    </Button>
                </DialogContent>
            </Dialog>
        </div>
    );
}

export default AdminManageSuggestions;
