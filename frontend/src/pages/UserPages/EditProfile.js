import * as React from "react";
import { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";
import profile from "../../images/comment-profile.png";
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBTypography,
} from "mdb-react-ui-kit";

import Modal from "@mui/material/Modal";

const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    boxShadow: 24,
    p: 4,
};

function isEmptyString(str) {
    // Check if the string is null, undefined, or consists only of whitespace characters
    return str === null || str === undefined || str.trim() === "";
}

function ValidateEmail(mail) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
        return true;
    }
    toast("You have entered an invalid email address!");
    return false;
}

export default function EditProfile() {
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();

        // localStorage.setItem(
        //     "newName",
        //     JSON.stringify(name)
        // );
        // localStorage.setItem(
        //     "newEmail",
        //     JSON.stringify(email)
        // );
        // localStorage.setItem(
        //     "newPassword",
        //     JSON.stringify(password)
        // );

        // try {
        //     const res = await myAxios.post(
        //         `${BASE_URL}/user/editProfile`,
        //         { },
        //         {
        //             headers: {
        //                 Authorization: `Bearer ${JSON.parse(
        //                     localStorage.getItem("token")
        //                 )}`,
        //             },
        //         }
        //     );
        //     console.log(res);
        //     if (res.status === 200) {
        //         toast("Post Added Successfully!!!");
        //     } else {
        //         toast("Failed To Edit Pofile!!!");
        //     }
        // } catch (error) {
        //     toast("Failed To Edit Profile!!!");
        // }
    };

    const [isUsernameModalOpen, setUsernameModalOpen] = useState(false);
    const [isPasswordModalOpen, setPasswordModalOpen] = useState(false);
    const [isEmailModalOpen, setEmailModalOpen] = useState(false);
    const [newUsername, setNewUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [newEmail, setNewEmail] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [passwordMismatch, setPasswordMismatch] = useState(false);

    const handleUsernameEdit = () => {
        setUsernameModalOpen(true);
    };

    const handlePasswordEdit = () => {
        setPasswordModalOpen(true);
    };

    const handleEmailEdit = () => {
        setEmailModalOpen(true);
    };

    const handleModalClose = () => {
        setUsernameModalOpen(false);
        setPasswordModalOpen(false);
        setEmailModalOpen(false);
        setPasswordMismatch(false);
        setPassword("");
        setConfirmPassword("");
    };

    const handleUsernameSave = async () => {
        if (isEmptyString(newUsername)) {
            toast("Username cannot be Empty!!!");
        } else {
            try {
                const res = await myAxios.post(
                    `${BASE_URL}/user/updateName`,
                    {
                        username: newUsername,
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
                    if (res.data.status === "OK") {
                        setName(newUsername);
                    }
                    toast(res.data.message);
                } else {
                    toast("Failed To Update Username!!!");
                }
            } catch (error) {
                toast("Failed To Update Username!!!");
            }
        }
        handleModalClose();
    };

    const handlePasswordSave = async () => {
        if (newPassword === confirmPassword) {
            if (isEmptyString(newPassword)) {
                toast("Password cannot be Empty!!!");
            } else {
                try {
                    const res = await myAxios.post(
                        `${BASE_URL}/user/updatePassword`,
                        {
                            password: newPassword,
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
                        toast("Failed To Update Password!!!");
                    }
                } catch (error) {
                    toast("Failed To Update Password!!!");
                }
            }
            handleModalClose();
        } else {
            setPasswordMismatch(true);
        }
    };

    const handleEmailSave = async () => {
        if (ValidateEmail(newEmail)) {
            try {
                const res = await myAxios.post(
                    `${BASE_URL}/user/sendOtp`,
                    {
                        email: newEmail,
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
                    if (res.data.status === "OK") {
                        localStorage.setItem("email", JSON.stringify(newEmail));
                        navigate("/user/emailVerificationOtp");
                    }
                    toast(res.data.message);
                } else {
                    toast("Failed To Update Password!!!");
                }
            } catch (error) {
                toast("Failed To Update Password!!!");
            }
        }
        handleModalClose();
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
                        setName(res.data.currentUser.name);
                        setEmail(res.data.currentUser.email);
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
        <section className="vh-100" style={{ backgroundColor: "#f4f5f7" }}>
            <ToastContainer />
            <MDBContainer className="py-5 h-100">
                <MDBRow className="justify-content-center align-items-center h-100">
                    <MDBCol lg="8" className="mb-4 mb-lg-0">
                        <MDBCard
                            className="mb-3"
                            style={{ borderRadius: ".5rem" }}
                        >
                            <MDBRow className="g-0">
                                <MDBCol
                                    md="4"
                                    className="gradient-custom text-center text-white"
                                    style={{
                                        borderTopLeftRadius: ".5rem",
                                        borderBottomLeftRadius: ".5rem",
                                    }}
                                >
                                    <MDBCardImage
                                        src={profile}
                                        alt="Avatar"
                                        className="my-5"
                                        style={{ width: "80px" }}
                                        fluid
                                    />
                                </MDBCol>
                                <MDBCol md="8">
                                    <MDBCardBody className="p-4">
                                        <MDBTypography tag="h6">
                                            Pofile
                                        </MDBTypography>
                                        <hr className="mt-0 mb-4" />
                                        <MDBRow className="pt-1">
                                            <MDBCol size="6" className="mb-3">
                                                <MDBTypography tag="h6">
                                                    Email
                                                </MDBTypography>
                                                <MDBCardText className="text-muted">
                                                    {email}
                                                </MDBCardText>
                                            </MDBCol>
                                            <MDBCol size="6" className="mb-3">
                                                <MDBTypography tag="h6">
                                                    Username
                                                </MDBTypography>
                                                <MDBCardText className="text-muted">
                                                    {name}
                                                </MDBCardText>
                                            </MDBCol>
                                        </MDBRow>

                                        <MDBTypography tag="h6">
                                            Actions
                                        </MDBTypography>
                                        <hr className="mt-0 mb-4" />
                                        <MDBRow className="pt-1">
                                            <MDBCol
                                                size="4"
                                                className="mb-3 d-flex"
                                            >
                                                <Button
                                                    variant="contained"
                                                    onClick={handleUsernameEdit}
                                                    className="flex-fill"
                                                >
                                                    Edit Username
                                                </Button>
                                            </MDBCol>
                                            <MDBCol
                                                size="4"
                                                className="mb-3 d-flex"
                                            >
                                                <Button
                                                    variant="contained"
                                                    onClick={handlePasswordEdit}
                                                    className="flex-fill"
                                                >
                                                    Edit Password
                                                </Button>
                                            </MDBCol>
                                            <MDBCol
                                                size="4"
                                                className="mb-3 d-flex"
                                            >
                                                <Button
                                                    variant="contained"
                                                    onClick={handleEmailEdit}
                                                    className="flex-fill"
                                                >
                                                    Edit Email
                                                </Button>
                                            </MDBCol>
                                        </MDBRow>
                                    </MDBCardBody>
                                </MDBCol>
                            </MDBRow>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>

            <Modal open={isUsernameModalOpen} onClose={handleModalClose}>
                <Box sx={style}>
                    <Typography variant="h6" component="h2">
                        Edit Username
                    </Typography>
                    <TextField
                        label="New Username"
                        variant="outlined"
                        fullWidth
                        value={newUsername}
                        sx={{ margin: "10px 0" }}
                        onChange={(e) => setNewUsername(e.target.value)}
                    />
                    <Button
                        variant="outlined"
                        sx={{ margin: "10px 0" }}
                        onClick={handleUsernameSave}
                    >
                        Save
                    </Button>
                </Box>
            </Modal>

            <Modal open={isPasswordModalOpen} onClose={handleModalClose}>
                <Box sx={style}>
                    <Typography variant="h6" component="h2">
                        Edit Password
                    </Typography>
                    <TextField
                        label="New Password"
                        variant="outlined"
                        fullWidth
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        sx={{ margin: "10px 0" }}
                    />
                    <TextField
                        label="Confirm Password"
                        variant="outlined"
                        fullWidth
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        error={passwordMismatch}
                        helperText={
                            passwordMismatch ? "Passwords do not match" : ""
                        }
                        sx={{ margin: "10px 0" }}
                    />
                    <Button
                        variant="outlined"
                        onClick={handlePasswordSave}
                        sx={{ margin: "10px 0" }}
                    >
                        Save
                    </Button>
                </Box>
            </Modal>

            <Modal open={isEmailModalOpen} onClose={handleModalClose}>
                <Box sx={style}>
                    <Typography variant="h6" component="h2">
                        Edit Email
                    </Typography>
                    <TextField
                        label="New Email"
                        variant="outlined"
                        fullWidth
                        value={newEmail}
                        sx={{ margin: "10px 0" }}
                        onChange={(e) => setNewEmail(e.target.value)}
                    />
                    <Button
                        variant="outlined"
                        sx={{ margin: "10px 0" }}
                        onClick={handleEmailSave}
                    >
                        Save
                    </Button>
                </Box>
            </Modal>
        </section>
    );
}
