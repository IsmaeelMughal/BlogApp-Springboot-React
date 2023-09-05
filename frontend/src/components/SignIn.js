import * as React from "react";
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

import "react-toastify/dist/ReactToastify.css";

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

export default function SignIn() {
    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        event.preventDefault();
        var token = null;
        const data = new FormData(event.currentTarget);

        try {
            const res = await myAxios.post(
                `${BASE_URL}/api/auth/authenticate`,
                {
                    email: data.get("email"),
                    password: data.get("password"),
                }
            );
            if (res.status === 200) {
                if (res.data.status === "OK") {
                    localStorage.setItem(
                        "token",
                        JSON.stringify(res.data.data.token)
                    );
                    localStorage.setItem(
                        "role",
                        JSON.stringify(res.data.data.role)
                    );
                    if (res.data.data.role === "ROLE_USER") {
                        navigate("/user");
                    } else if (res.data.data.role === "ROLE_ADMIN") {
                        navigate("/admin");
                    } else if (res.data.data.role === "ROLE_MODERATOR") {
                        navigate("/moderator");
                    }
                    toast("Login Successfull!!!");
                }
            } else {
                toast("Server is Busy Please Try Again Later!!!");
            }
        } catch {
            toast("Server is Busy Please Try Again Later!!!");
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
                            Sign in
                        </Typography>
                        <Box
                            component="form"
                            onSubmit={handleSubmit}
                            noValidate
                            sx={{ mt: 1 }}
                        >
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                autoFocus
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                            />

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                            >
                                Sign In
                            </Button>
                            <Grid container>
                                <Grid item>
                                    <Link href="/signup" variant="body2">
                                        {"Don't have an account? Sign Up"}
                                    </Link>
                                </Grid>
                            </Grid>
                        </Box>
                    </Box>
                    <Copyright sx={{ mt: 8, mb: 4 }} />
                </Container>
            </ThemeProvider>
        </div>
    );
}
