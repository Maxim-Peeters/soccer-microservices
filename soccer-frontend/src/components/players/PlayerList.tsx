import  { useState } from 'react';
import PlayerService from '../../services/playerService';
import PlayerCard from './PlayerCard';
import { PlayerResponse } from '../../dto/PlayerResponse';

function PlayerList() {
  const [players, setPlayers] = useState<PlayerResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);


    const fetchPlayers = async () => {
      try {
        const fetchedPlayers = await PlayerService.getAllPlayers();
        setPlayers(fetchedPlayers);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch players');
        setLoading(false);
      }
    };

    fetchPlayers();
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {players.map(player => (
        <PlayerCard key={player.playerCode} player={player} />
      ))}
    </div>
  );
}

export default PlayerList;

