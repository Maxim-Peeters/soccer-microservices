import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/Home";
import LoginPage from "./pages/Login";
import MatchesPage from "./pages/Matches";
import TeamsPage from "./pages/Teams";
import SupportersPage from "./pages/Supporters";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { AuthProvider } from "./context/AuthContext";
import TeamCrud from "./components/teams/TeamCrud";
import NavBar from "./components/NavBar";
const App: React.FC = () => {
  
  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_AUTH_CLIENTID}>
      <AuthProvider>
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/matches" element={<MatchesPage />} />
          <Route path="/teams" element={<TeamsPage />} />
          <Route path="/manage-teams" element={<TeamCrud />} />
          <Route path="/supporters" element={<SupportersPage />} />
        </Routes>
      </Router>
      </AuthProvider>
    </GoogleOAuthProvider>
  );
};

export default App;
