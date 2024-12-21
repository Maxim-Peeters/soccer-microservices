import React, { useEffect, useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { TeamResponse } from '../../dto/TeamResponse';
import TeamService from '../../services/teamService';
import { LoadingSpinner } from '../LoadingSpinner';
import { PlayerResponse } from '../../dto/PlayerResponse';
import { FaChevronDown, FaChevronRight } from 'react-icons/fa';
import AddEditTeam from './AddEditTeam'; // Import the new component
import AddEditPlayer from '../players/AddEditPlayer'; // Import the new component
import PlayerService from '../../services/playerService';

const TeamCrud: React.FC = () => {
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [teams, setTeams] = useState<TeamResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expandedTeams, setExpandedTeams] = useState<{ [key: string]: boolean }>({});
    const [isAddEditOpen, setIsAddEditOpen] = useState(false);
    const [selectedTeam, setSelectedTeam] = useState<TeamResponse | null>(null);
    const [isAddEditPlayerOpen, setIsAddEditPlayerOpen] = useState(false);
    const [selectedPlayer, setSelectedPlayer] = useState<PlayerResponse | null>(null);
    const fetchTeams = async () => {
        try {
            setLoading(true);
            setError(null);
            const fetchedTeams = await TeamService.getAllTeams();
            setTeams(fetchedTeams);
        } catch (err) {
            setError('Failed to fetch teams');
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        if (!isAuthenticated) {
            window.alert("You are not authenticated, please login first");
            navigate('/', { replace: true });
            return;
        }
        fetchTeams();
    }, [isAuthenticated, navigate]);

    const toggleTeamExpansion = (teamCode: string) => {
        setExpandedTeams(prevState => ({
            ...prevState,
            [teamCode]: !prevState[teamCode]
        }));
    }

    const openAddEdit = (team?: TeamResponse) => {
        setSelectedTeam(team || null);
        setIsAddEditOpen(true);
    };

    const closeAddEdit = () => {
        setIsAddEditOpen(false);
        setSelectedTeam(null);
        fetchTeams(); // Refresh the team list after adding/editing
    };

    const openAddEditPlayer = (player?: PlayerResponse, teamCode?: string) => {
        setSelectedPlayer(player || null);
        setIsAddEditPlayerOpen(true);
        if (teamCode) {
            setSelectedTeam(teams.find(team => team.teamCode === teamCode) || null);
        }
    };

    const closeAddEditPlayer = () => {
        setIsAddEditPlayerOpen(false);
        setSelectedPlayer(null);
        fetchTeams(); // Refresh the team list after adding/editing a player
    };

    const handleDeleteTeam = async (teamCode: string) => {
        if (window.confirm('Are you sure you want to delete this team?')) {
            try {
                await TeamService.deleteTeam(teamCode);
                fetchTeams();
            } catch (err) {
                setError('Failed to delete team');
            }
        }
    };

    const handleDeletePlayer = async (playerCode: string) => {
        if (window.confirm('Are you sure you want to delete this player?')) {
            try {
                await PlayerService.deletePlayer(playerCode);
                fetchTeams();
            } catch (err) {
                setError('Failed to delete player');
            }
        }
    };

    if (loading) {
        return (
            <LoadingSpinner />
        );
    }

    if (error) {
        return (
            <div className="flex flex-col items-center justify-center min-h-[400px] space-y-4">
                <p className="text-red-500 text-lg">{error}</p>
                <button 
                    onClick={() => fetchTeams()}
                    className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                >
                    Retry
                </button>
            </div>
        );
    }

    if (teams.length === 0) {
        return (
            <div className="text-center py-8 text-gray-500">
                No teams available
            </div>
        );
    }

    const sortedTeams = [...teams].sort((a, b) => a.name.localeCompare(b.name));

    return (
        <div className="container mx-auto px-4">
            <h1 className="text-4xl font-bold mb-8 text-center">Team Management</h1>
            <button 
                onClick={() => openAddEdit()}
                className="mb-4 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            >
                Create New Team
            </button>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b text-center"></th>
                            <th className="py-2 px-4 border-b text-center">Team Name</th>
                            <th className="py-2 px-4 border-b text-center">City</th>
                            <th className="py-2 px-4 border-b text-center">Country</th>
                            <th className="py-2 px-4 border-b text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedTeams.map((team) => (
                            <React.Fragment key={team.teamCode}>
                                <tr>
                                    <td className="py-2 px-4 border-b text-center">
                                        <button onClick={() => toggleTeamExpansion(team.teamCode)}>
                                            {expandedTeams[team.teamCode] ? <FaChevronDown /> : <FaChevronRight />}
                                        </button>
                                    </td>
                                    <td className="py-2 px-4 border-b text-center">{team.name}</td>
                                    <td className="py-2 px-4 border-b text-center">{team.city}</td>
                                    <td className="py-2 px-4 border-b text-center">{team.country}</td>
                                    <td className="py-2 px-4 border-b text-center">
                                        <button 
                                            onClick={() => openAddEdit(team)}
                                            className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition-colors"
                                        >
                                            Edit
                                        </button>
                                        <button 
                                            onClick={() => handleDeleteTeam(team.teamCode)}
                                            className="ml-2 px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors"
                                        >
                                            Delete
                                        </button>
                                        <button 
                                            onClick={() => openAddEditPlayer(undefined, team.teamCode)}
                                            className="ml-2 px-2 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition-colors"
                                        >
                                            Add Player
                                        </button>
                                    </td>
                                </tr>
                                <tr className={`transition-all duration-500 ease-in-out ${expandedTeams[team.teamCode] ? 'h-auto opacity-100' : 'h-0 opacity-0 overflow-hidden'}`}>
                                    <td colSpan={5}>
                                        <div className={`transition-all duration-500 ease-in-out ${expandedTeams[team.teamCode] ? 'max-h-[1000px]' : 'max-h-0 overflow-hidden'}`}>
                                            <table className="min-w-full bg-gray-100">
                                                <thead>
                                                    <tr>
                                                        <th className="py-2 px-4 border-b text-center">First Name</th>
                                                        <th className="py-2 px-4 border-b text-center">Last Name</th>
                                                        <th className="py-2 px-4 border-b text-center">Position</th>
                                                        <th className="py-2 px-4 border-b text-center">Birth Date</th>
                                                        <th className="py-2 px-4 border-b text-center">Nationality</th>
                                                        <th className="py-2 px-4 border-b text-center">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {team.players.sort((a, b) => a.lastName.localeCompare(b.lastName)).map((player: PlayerResponse) => (
                                                        <tr key={player.playerCode}>
                                                            <td className="py-2 px-4 border-b text-center">{player.firstName}</td>
                                                            <td className="py-2 px-4 border-b text-center">{player.lastName}</td>
                                                            <td className="py-2 px-4 border-b text-center">{player.position}</td>
                                                            <td className="py-2 px-4 border-b text-center">{player.birthDate}</td>
                                                            <td className="py-2 px-4 border-b text-center">{player.nationality}</td>
                                                            <td className="py-2 px-4 border-b text-center">
                                                                <button className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition-colors"
                                                                    onClick={() => openAddEditPlayer(player)}>
                                                                    Edit
                                                                </button>
                                                                <button className="ml-2 px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors"
                                                                    onClick={() => handleDeletePlayer(player.playerCode)}>
                                                                    Delete
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    ))}
                                                </tbody>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </React.Fragment>
                        ))}
                    </tbody>
                </table>
            </div>
            {isAddEditOpen && <AddEditTeam onClose={closeAddEdit} team={selectedTeam} />}
            {isAddEditPlayerOpen && <AddEditPlayer onClose={closeAddEditPlayer} player={selectedPlayer} teamCode={selectedTeam?.teamCode || ''} />}
        </div>
    );
}

export default TeamCrud;
