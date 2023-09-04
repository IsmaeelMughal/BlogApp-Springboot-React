import React, { useState } from "react";
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardFooter,
    MDBCardImage,
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBTextArea,
    MDBTypography,
} from "mdb-react-ui-kit";
import { ToastContainer, toast } from "react-toastify";

import UserProfileIcon from "../images/comment-profile.png";
import { BASE_URL, myAxios } from "../services/AxiosHelper";

export default function SuggestionBox({ postId }) {
    const [suggestionText, setSuggestionText] = useState("");

    const handleSuggestionTextChange = (e) => {
        setSuggestionText(e.target.value);
    };

    const handleAddSuggestionClick = async () => {
        console.log(suggestionText);
        if (suggestionText.length > 990) {
            toast("Suggestion is too long!!!");
        } else {
            try {
                const res = await myAxios.post(
                    `${BASE_URL}/post/addSuggestion`,
                    {
                        postId: postId,
                        suggestion: suggestionText,
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
                    setSuggestionText("");
                } else {
                    toast(res.data.message);
                }
            } catch (err) {
                toast("Failed To Suggest!!!");
            }
        }
    };

    return (
        <section style={{ backgroundColor: "#eee" }}>
            <ToastContainer />

            <MDBContainer className="py-5" style={{ maxWidth: "1000px" }}>
                <MDBRow className="justify-content-center">
                    <MDBCol md="12" lg="10" xl="8">
                        <MDBCard>
                            <MDBCardBody>
                                <MDBTypography tag="h4" className="mb-0">
                                    Suggestion Box
                                </MDBTypography>
                            </MDBCardBody>

                            <MDBCardFooter
                                className="py-3 border-0"
                                style={{ backgroundColor: "#f8f9fa" }}
                            >
                                <div className="d-flex flex-start w-100">
                                    <MDBCardImage
                                        className="rounded-circle shadow-1-strong me-3"
                                        src={UserProfileIcon}
                                        alt="avatar"
                                        width="40"
                                        height="40"
                                    />
                                    <MDBTextArea
                                        label="Message"
                                        id="textAreaExample"
                                        rows={4}
                                        style={{ backgroundColor: "#fff" }}
                                        wrapperClass="w-100"
                                        value={suggestionText}
                                        onChange={handleSuggestionTextChange}
                                    />
                                </div>
                                <div className="float-end mt-2 pt-1">
                                    <MDBBtn
                                        size="sm"
                                        className="me-1"
                                        onClick={handleAddSuggestionClick}
                                    >
                                        Add Suggestion
                                    </MDBBtn>
                                </div>
                            </MDBCardFooter>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </section>
    );
}
