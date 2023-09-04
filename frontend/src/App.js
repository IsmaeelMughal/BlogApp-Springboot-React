import { Route, Routes } from "react-router-dom";
import "./App.css";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
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
import UserSuggestionPage from "./pages/UserPages/UserSuggestionPage";
import EditPost from "./pages/UserPages/EditPost";
import AdminShowAllPosts from "./pages/AdminPages/AdminShowAllPosts";
import AdminReportedPosts from "./pages/AdminPages/AdminReportedPosts";
import AdminManageSuggestions from "./pages/AdminPages/AdminManageSuggestions";
import AdminManageReportedComments from "./pages/AdminPages/AdminManageReportedComments";
import AdminSinglePostDetails from "./pages/AdminPages/AdminSinglePostDetails";

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
                <Route path="suggestions" element={<UserSuggestionPage />} />
                <Route path="editPost/:postId" element={<EditPost />} />
            </Route>

            <Route path="/admin" element={<AdminLayout />}>
                <Route index element={<AdminHome />} />
                <Route path="manageUsers" element={<AdminManageUsers />} />
                <Route path="addModerator" element={<AdminAddModerator />} />
                <Route
                    path="manageModerator"
                    element={<AdminManageModerator />}
                />
                <Route path="showAllPosts" element={<AdminShowAllPosts />} />
                <Route
                    path="showReportedPosts"
                    element={<AdminReportedPosts />}
                />
                <Route
                    path="manageSuggestions"
                    element={<AdminManageSuggestions />}
                />
                <Route
                    path="manageReportedComments"
                    element={<AdminManageReportedComments />}
                />
                <Route
                    path="post/:postId"
                    element={<AdminSinglePostDetails />}
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
