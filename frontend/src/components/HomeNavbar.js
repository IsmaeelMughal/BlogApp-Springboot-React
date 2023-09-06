import React from "react";
import { useNavigate } from "react-router-dom";

function HomeNavbar() {
    const navigate = useNavigate();
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
        </div>
    );
}

export default HomeNavbar;
