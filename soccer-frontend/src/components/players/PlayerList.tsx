import PlayerCard from "./PlayerCard";
import PlayerService from "../../services/playerService";
import { useAuthenticatedFetch } from "../../hooks/useAuthenticatedFetch";

function PlayerListComponent() {
  const { data: players, loading, error } = useAuthenticatedFetch(PlayerService.get);

  if (loading) {
    return <p>Loading players...</p>;
  }

  if (error) {
    return <p>Error loading players: {error.message}</p>;
  }

  if (!players) {
    return <p>No players found or you may need to log in.</p>;
  }

  return (
    <div>
      {players.map((player) => (
        <PlayerCard key={player.playerCode} player={player} />
      ))}
    </div>
  );
}

export default PlayerListComponent;

