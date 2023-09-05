import React, { useEffect } from "react";
import UserSuggestionsOnOthersPosts from "./UserSuggestionsOnOthersPosts";
import SuggestionsOnUserPosts from "./SuggestionsOnUserPosts";
import { useNavigate } from "react-router-dom";
import { BASE_URL, myAxios } from "../../services/AxiosHelper";

function UserSuggestionPage() {
    const navigate = useNavigate();
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
        <div className="container my-5">
            <UserSuggestionsOnOthersPosts />
            <SuggestionsOnUserPosts />
        </div>
    );
}

export default UserSuggestionPage;
