import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { PlayerResponse } from "../../dto/PlayerResponse";
import { TeamResponse } from "../../dto/TeamResponse";
import { useNavigate } from "react-router-dom";
import PlayerService from "../../services/playerService";
import TeamService from "../../services/teamService";

function PlayerCrud() {
  const { isAuthenticated } = useAuth();
  const [players, setPlayers] = useState<PlayerResponse[]>([]);
  const [teams, setTeams] = useState<TeamResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    const fetchAllData = async () => {
      try {
        // Fetch players
        const fetchedPlayers = await PlayerService.getAllPlayers();
        setPlayers(fetchedPlayers);

        // Fetch all teams
        const fetchedTeams = await TeamService.getAllTeams();
        setTeams(fetchedTeams);

        setLoading(false);
      } catch (err) {
        setError('Failed to fetch players or teams');
        setLoading(false);
      }
    };

    fetchAllData();
  }, [isAuthenticated, navigate]);

  const handleEdit = (playerId: string) => {
    navigate(`/edit-player/${playerId}`);
  };

  const handleDelete = async (playerId: string) => {
    try {
      const response = await PlayerService.deletePlayer(playerId);
      if (response === "Player deleted") {
        setPlayers(players.filter(player => player.playerCode !== playerId));
      }
    } catch (err) {
      setError('Failed to delete player');
    }
  };

  if (loading) return <div className="text-center text-lg text-gray-600">Loading...</div>;
  if (error) return <div className="text-center text-lg text-red-600">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
      <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">Players List</h1>
      <div className="overflow-x-auto bg-white shadow-md rounded-lg">
        <table className="min-w-full table-auto text-left">
          <thead className="bg-gray-200 text-gray-700">
            <tr>
              <th className="px-6 py-3 text-sm font-medium">Full Name</th>
              <th className="px-6 py-3 text-sm font-medium">Position</th>
              <th className="px-6 py-3 text-sm font-medium">Team Name</th>
              <th className="px-6 py-3 text-sm font-medium">Birth Date</th>
              <th className="px-6 py-3 text-sm font-medium">Nationality</th>
              <th className="px-6 py-3 text-sm font-medium">Actions</th>
            </tr>
          </thead>
          <tbody className="text-gray-800">
            {players.map(player => (
              <tr key={player.playerCode} className="hover:bg-gray-50">
                <td className="px-6 py-4 text-sm">{`${player.firstName} ${player.lastName}`}</td>
                <td className="px-6 py-4 text-sm">{player.position}</td>
                <td className="px-6 py-4 text-sm">
                {/* {teams.find(team => team.teamCode === player.teamCode)?.name || 'Team not found'} */}
                  </td>
                <td className="px-6 py-4 text-sm">{new Date(player.birthDate).toLocaleDateString()}</td>
                <td className="px-6 py-4 text-sm">{player.nationality}</td>
                <td className="px-6 py-4 text-sm">
                  <button 
                    onClick={() => handleEdit(player.playerCode)} 
                    className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg mr-2 transition-colors duration-300"
                  >
                    Edit
                  </button>
                  <button 
                    onClick={() => handleDelete(player.playerCode)} 
                    className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded-lg transition-colors duration-300"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default PlayerCrud;
