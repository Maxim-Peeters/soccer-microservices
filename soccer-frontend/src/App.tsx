import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Players from "./pages/Players";
import { AuthProvider } from "./contexts/AuthContext";
import Login from "./pages/Login";

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/players" element={<Players />} />
          <Route path="/" element={<Login />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
