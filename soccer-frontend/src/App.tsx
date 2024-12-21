import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/Home";
import MatchesPage from "./pages/Matches";
import TeamsPage from "./pages/Teams";
import SupportersPage from "./pages/Supporters";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { AuthProvider } from "./context/AuthContext";
import TeamCrud from "./components/teams/TeamCrud";
import NavBar from "./components/NavBar";
import MatchCrud from "./components/matches/MatchCrud";
import SupporterCrud from "./components/supporters/SupporterCrud";
const App: React.FC = () => {
  
  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_AUTH_CLIENTID}>
      <AuthProvider>
      <Router>
        <NavBar />
        <Routes>
          <Route path="/matches" element={<MatchesPage />} />
          <Route path="/teams" element={<TeamsPage />} />
          <Route path="/manage-teams" element={<TeamCrud />} />
          <Route path="/manage-matches" element={<MatchCrud />} />
          <Route path="/manage-supporters" element={<SupporterCrud />} />
          <Route path="/supporters" element={<SupportersPage />} />
          <Route path="/" element={<HomePage />} />
        </Routes>
      </Router>
      </AuthProvider>
    </GoogleOAuthProvider>
  );
};

export default App;
