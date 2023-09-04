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
import { Delete } from "@mui/icons-material";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";

function AdminManageModerator() {
    const [rows, setRows] = useState([]);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await myAxios.get(
                    `${BASE_URL}/admin/getAllModerators`,
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
                    toast("Total Moderators Count: " + res.data.data.length);
                } else {
                    toast("Failed To Fetch Moderators");
                }
            } catch (err) {
                toast("Failed To Fetch Moderators");
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
                                <h5>Moderator Id</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Username</h5>
                            </TableCell>
                            <TableCell align="right">
                                <h5>Email</h5>
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
                                    {row.id}
                                </TableCell>
                                <TableCell align="right">{row.name}</TableCell>
                                <TableCell align="right">{row.email}</TableCell>
                                <TableCell align="right">
                                    <div>
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
                                                            "Moderator Deleted Successfully!!!"
                                                        );
                                                    } else {
                                                        toast(
                                                            "Failed To Delete Moderator"
                                                        );
                                                    }
                                                } catch (err) {
                                                    toast(
                                                        "Failed To Delete Moderator"
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

export default AdminManageModerator;
