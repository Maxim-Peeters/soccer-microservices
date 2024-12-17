import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import PlayerListComponent from "../components/players/PlayerList";
import { useAuth } from "../contexts/AuthContext";

function PlayersPage() {
  const { token } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      navigate("/login");
    }
  }, [token, navigate]);
  return (
    <div>
      <h1>Players</h1>
      <PlayerListComponent />
    </div>
  );
}

export default PlayersPage;

