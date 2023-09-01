import React from "react";
import { Link } from "react-router-dom";

function UserHeader() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">
                        Blog App
                    </Link>
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
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    aria-current="page"
                                    to="/user"
                                >
                                    Home Page
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/user/newpost">
                                    New Post
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/user/mypost">
                                    My Posts
                                </Link>
                            </li>
                        </ul>
                        <div className="d-flex">
                            <button className="btn btn-outline-primary">
                                Logout
                            </button>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
}

export default UserHeader;