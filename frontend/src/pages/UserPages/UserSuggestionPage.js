import React from "react";
import UserSuggestionsOnOthersPosts from "./UserSuggestionsOnOthersPosts";
import SuggestionsOnUserPosts from "./SuggestionsOnUserPosts";

function UserSuggestionPage() {
    return (
        <div className="container my-5">
            <UserSuggestionsOnOthersPosts />
            <SuggestionsOnUserPosts />
        </div>
    );
}

export default UserSuggestionPage;
