import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import HomePage from "./pages/Home";
import LoginPage from "./pages/Login";
import PlayersPage from "./pages/Players";
import MatchesPage from "./pages/Matches";
import TeamsPage from "./pages/Teams";
import SupportersPage from "./pages/Supporters";

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/players" element={<PlayersPage />} />
          <Route path="/matches" element={<MatchesPage />} />
          <Route path="/teams" element={<TeamsPage />} />
          <Route path="/supporters" element={<SupportersPage />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
