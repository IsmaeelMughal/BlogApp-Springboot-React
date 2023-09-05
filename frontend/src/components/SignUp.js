import * as React from "react";
import { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { BASE_URL, myAxios } from "../services/AxiosHelper";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function Copyright(props) {
    return (
        <Typography
            variant="body2"
            color="text.secondary"
            align="center"
            {...props}
        >
            {"Copyright © "}
            <Link color="inherit" href="/">
                Blog App
            </Link>{" "}
            {new Date().getFullYear()}
            {"."}
        </Typography>
    );
}

const defaultTheme = createTheme();

export default function SignUp() {
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState({
        name: "",
        email: "",
        password: "",
    });

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const res = await myAxios.post(`${BASE_URL}/api/auth/register`, {
                name: userDetails.name,
                email: userDetails.email,
                password: userDetails.password,
                role: "ROLE_USER",
            });
            if (res.status === 200) {
                if (res.data.status === "OK") {
                    localStorage.setItem(
                        "email",
                        JSON.stringify(userDetails.email)
                    );
                    navigate("/otpVerification");
                } else {
                    toast(res.data.message);
                }
            } else {
                toast(res.data.message);
            }
        } catch (error) {
            toast("Regestration Failed!!!");
        }
    };

    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <span className="navbar-brand" to="/">
                        <h2>Blog App</h2>
                    </span>
                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarTogglerDemo02"
                        aria-controls="navbarTogglerDemo02"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div
                        className="collapse navbar-collapse"
                        id="navbarTogglerDemo02"
                    >
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0"></ul>
                        <div className="d-flex">
                            <button
                                className="btn btn-outline-primary mx-3"
                                onClick={() => {
                                    navigate("/");
                                }}
                            >
                                Login
                            </button>
                            <button
                                className="btn btn-outline-primary mx-3"
                                onClick={() => {
                                    navigate("/signup");
                                }}
                            >
                                SignUp
                            </button>
                        </div>
                    </div>
                </div>
            </nav>
            <ThemeProvider theme={defaultTheme}>
                <ToastContainer />
                <Container component="main" maxWidth="xs">
                    <CssBaseline />
                    <Box
                        sx={{
                            marginTop: 8,
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "center",
                        }}
                    >
                        <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                            <LockOutlinedIcon />
                        </Avatar>
                        <Typography component="h1" variant="h5">
                            Sign up
                        </Typography>
                        <Box
                            component="form"
                            onSubmit={handleSubmit}
                            sx={{ mt: 3 }}
                        >
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="name"
                                        label="name"
                                        name="name"
                                        autoComplete="family-name"
                                        onChange={(e) =>
                                            setUserDetails((prev) => ({
                                                ...prev,
                                                name: e.target.value,
                                            }))
                                        }
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="email"
                                        label="Email Address"
                                        type="email"
                                        name="email"
                                        autoComplete="email"
                                        onChange={(e) =>
                                            setUserDetails((prev) => ({
                                                ...prev,
                                                email: e.target.value,
                                            }))
                                        }
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        name="password"
                                        label="Password"
                                        type="password"
                                        id="password"
                                        onChange={(e) =>
                                            setUserDetails((prev) => ({
                                                ...prev,
                                                password: e.target.value,
                                            }))
                                        }
                                        autoComplete="new-password"
                                    />
                                </Grid>
                            </Grid>

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                            >
                                Sign Up
                            </Button>
                            <Grid container justifyContent="flex-end">
                                <Grid item>
                                    <Link href="/" variant="body2">
                                        Already have an account? Sign in
                                    </Link>
                                </Grid>
                            </Grid>
                        </Box>
                    </Box>
                    <Copyright sx={{ mt: 5 }} />
                </Container>
            </ThemeProvider>
        </div>
    );
}
