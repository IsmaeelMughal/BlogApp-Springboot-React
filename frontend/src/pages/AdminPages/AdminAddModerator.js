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
import { ToastContainer, toast } from "react-toastify";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";

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

function AdminAddModerator() {
    const [error, setError] = useState({ message: "" });

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
                role: "ROLE_MODERATOR",
            });
            console.log(res);
            const jwt = res.data.token;
            if (jwt === "") {
                setError({
                    message: "Email Already in Use!!!",
                });
            } else {
                toast("Registered Successfully!!!");
            }
        } catch (error) {
            setError({
                message: "Server is busy!!!",
            });
        }
    };
    return (
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
                        Add Moderator
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
                        <Grid>
                            {error.message === "" ? (
                                ""
                            ) : (
                                <p className="text-danger font-weight-bold my-2">
                                    {error.message}
                                </p>
                            )}
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign Up
                        </Button>
                    </Box>
                </Box>
                <Copyright sx={{ mt: 5 }} />
            </Container>
        </ThemeProvider>
    );
}

export default AdminAddModerator;
