import React from "react";
import commentProfileImage from "../../images/comment-profile.png";
import parse from "html-react-parser";

function Reply({ replyData, currentUser }) {
    return (
        <div>
            <div className="d-flex flex-start mt-4">
                <span className="me-3">
                    <img
                        className="rounded-circle shadow-1-strong"
                        src={commentProfileImage}
                        alt="avatar"
                        width="65"
                        height="65"
                    />
                </span>
                <div className="flex-grow-1 flex-shrink-1">
                    <div>
                        <div className="d-flex justify-content-between align-items-center">
                            <span className="mb-1">
                                <span className="font-weight-bold">
                                    {replyData.repliedBy.name}
                                </span>
                                {currentUser.id === replyData.repliedBy.id ? (
                                    <span className="bg-warning rounded p-1 m-3">
                                        you
                                    </span>
                                ) : (
                                    ""
                                )}
                            </span>
                        </div>
                        <span className="small mb-0">
                            {parse(replyData.content)}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Reply;
