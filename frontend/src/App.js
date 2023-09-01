import { Route, Routes } from "react-router-dom";
import "./App.css";
import SignIn from "./components/SignIn";
import SignUp from "./components/SignUp";

import UserSinglePost from "./pages/UserPages/UserSinglePost";
import UserLayout from "./layout/userLayout/UserLayout";
import UserHome from "./pages/UserPages/UserHome";
import NewPost from "./pages/UserPages/NewPost";
import UserPosts from "./pages/UserPages/UserPosts";
import AdminLayout from "./layout/adminLayout/AdminLayout";
import ModeratorLayout from "./layout/moderatorLayout/ModeratorLayout";
import AdminHome from "./pages/AdminPages/AdminHome";
import ModeratorUnapprovedPosts from "./pages/ModeratorPages/ModeratorUnapprovedPosts";
import ModeratorSinglePostDetails from "./pages/ModeratorPages/ModeratorSinglePostDetails";
import ModeratorReportedPosts from "./pages/ModeratorPages/ModeratorReportedPosts";
import ModeratorSingleReportedPost from "./pages/ModeratorPages/ModeratorSingleReportedPost";
import AdminManageUsers from "./pages/AdminPages/AdminManageUsers";
import AdminAddModerator from "./pages/AdminPages/AdminAddModerator";
import AdminManageModerator from "./pages/AdminPages/AdminManageModerator";

function App() {
    return (
        <Routes>
            <Route path="/" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/user" element={<UserLayout />}>
                <Route index element={<UserHome />} />
                <Route path="newpost" element={<NewPost />} />
                <Route path="mypost" element={<UserPosts />} />
                <Route path="post/:postId" element={<UserSinglePost />} />
            </Route>

            <Route path="/admin" element={<AdminLayout />}>
                <Route index element={<AdminHome />} />
                <Route path="manageUsers" element={<AdminManageUsers />} />
                <Route path="addModerator" element={<AdminAddModerator />} />
                <Route
                    path="manageModerator"
                    element={<AdminManageModerator />}
                />
            </Route>

            <Route path="/moderator" element={<ModeratorLayout />}>
                <Route index element={<ModeratorUnapprovedPosts />} />
                <Route
                    path="post/:postId"
                    element={<ModeratorSinglePostDetails />}
                />

                <Route
                    path="reportedPosts"
                    element={<ModeratorReportedPosts />}
                />

                <Route
                    path="post/reported/:postId"
                    element={<ModeratorSingleReportedPost />}
                />
            </Route>
        </Routes>
    );
}

export default App;
