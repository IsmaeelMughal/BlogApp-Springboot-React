import React from "react";
import { Link, useNavigate } from "react-router-dom";

function AdminHeader() {
    const navigate = useNavigate();
    const handleLogoutClick = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        navigate("/");
    };
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">
                        <h2>Blog App - Admin</h2>
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
                                    to="/admin"
                                >
                                    Dashboard
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/manageUsers"
                                >
                                    Manage Users
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/addModerator"
                                >
                                    Add Moderator
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/manageModerator"
                                >
                                    Manage Moderator
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/showAllPosts"
                                >
                                    Show Posts
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/manageSuggestions"
                                >
                                    Show Suggestions
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/showReportedPosts"
                                >
                                    Show Reported Posts
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link
                                    className="nav-link"
                                    to="/admin/manageReportedComments"
                                >
                                    Show Reported Comments
                                </Link>
                            </li>
                        </ul>
                        <div className="d-flex">
                            <button
                                onClick={handleLogoutClick}
                                className="btn btn-outline-primary"
                            >
                                Logout
                            </button>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
}

export default AdminHeader;
