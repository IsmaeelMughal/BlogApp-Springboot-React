import React, { useState, useEffect } from "react";
import { MuiOtpInput } from "mui-one-time-password-input";
import { ToastContainer, toast } from "react-toastify";
import { BASE_URL, myAxios } from "../services/AxiosHelper";
import { useNavigate } from "react-router-dom";

const validateChar = (value, index) => {
    return /^[0-9]$/.test(value);
};

function OtpVerification() {
    const [otp, setOtp] = useState("");
    const navigate = useNavigate();

    const [userEmail, setUserEmail] = useState("");

    const handleChange = (newValue) => {
        setOtp(newValue);
    };

    useEffect(() => {
        setUserEmail(JSON.parse(localStorage.getItem("email")));

        toast(`Please Check your mail!!!`);
    }, []);

    const handleOTPSubmit = async (event) => {
        event.preventDefault();
        try {
            const res = await myAxios.post(`${BASE_URL}/api/auth/verifyOtp`, {
                email: userEmail,
                otp: otp,
            });
            if (res.status === 200) {
                if (res.data.status === "OK") {
                    toast(res.data.message);
                    toast(
                        "You will be directed to sign in page in few seconds!!!"
                    );
                    localStorage.removeItem("email");
                    setTimeout(() => {
                        if (
                            JSON.parse(localStorage.getItem("token")) ===
                            "ROLE_USER"
                        ) {
                            navigate("/");
                        } else {
                            navigate("/admin/addModerator");
                        }
                    }, 6000);
                } else {
                    toast(res.data.message);
                }
            } else {
                toast(res.data.message);
            }
        } catch (error) {
            toast("Failed to Verify!!!");
        }
    };

    return (
        <div className="container">
            <ToastContainer />
            <section class="wrapper d-flex align-items-center justify-content-center vh-100">
                <div class="container">
                    <div class="col-sm-8 offset-sm-2 col-lg-6 offset-lg-3 col-xl-6 offset-xl-3 text-center">
                        <form
                            onSubmit={handleOTPSubmit}
                            class="rounded bg-white shadow p-5"
                        >
                            <h3 class="text-dark fw-bolder fs-4 mb-2">
                                Two Step Verification
                            </h3>

                            <div class="fw-normal text-muted mb-4">
                                Enter the verification code we sent to
                            </div>
                            <div class="d-flex align-items-center justify-content-center fw-bold mb-4">
                                <span>{userEmail}</span>
                            </div>

                            <div class="otp_input text-start mb-2">
                                <label for="digit">
                                    Type your 6 digit OTP code
                                </label>
                                <div class="d-flex align-items-center justify-content-between mt-2">
                                    <MuiOtpInput
                                        value={otp}
                                        onChange={handleChange}
                                        length={6}
                                        TextFieldsProps={{ placeholder: "-" }}
                                        validateChar={validateChar}
                                    />
                                </div>
                            </div>

                            <button
                                type="submit"
                                class="btn btn-primary submit_btn my-4"
                            >
                                Submit
                            </button>

                            {/* <div class="fw-normal text-muted mb-2">
                                Didn’t get the code ?{" "}
                                <span
                                    class="text-primary fw-bold text-decoration-none"
                                    style={{ cursor: "pointer;" }}
                                >
                                    Resend
                                </span>
                            </div> */}
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default OtpVerification;
