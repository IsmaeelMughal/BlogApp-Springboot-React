import React from "react";
import { Image } from "react-bootstrap";
import { Link } from "react-router-dom";

function PostCard({ postData }) {
    return (
        <div>
            <div className="card my-5">
                <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    {postData.status}
                </span>
                <Image
                    className="card-img-top"
                    src={postData.image}
                    alt="Card image cap"
                />

                <div className="card-body">
                    <h5 className="card-title">{postData.title}</h5>
                    <p className="card-text">
                        Posted by{" "}
                        <span>
                            {postData.postedBy
                                ? postData.postedBy.name
                                : "Anonymous"}
                        </span>{" "}
                        on {postData.date}
                    </p>
                    <Link
                        to={`/user/post/${postData.id}`}
                        className="btn btn-primary"
                    >
                        Read More
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default PostCard;
