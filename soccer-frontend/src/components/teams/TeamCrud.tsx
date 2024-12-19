import React, { useEffect, useState } from 'react';
import { TeamResponse } from '../../dto/TeamResponse';
import TeamService from '../../services/teamService';
import { LoadingSpinner } from '../LoadingSpinner';
import { PlayerResponse } from '../../dto/PlayerResponse';
import { FaChevronDown, FaChevronRight } from 'react-icons/fa';
import AddEditTeam from './AddEditTeam'; // Import the new component

const TeamCrud: React.FC = () => {
    const [teams, setTeams] = useState<TeamResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expandedTeams, setExpandedTeams] = useState<{ [key: string]: boolean }>({});
    const [isAddEditOpen, setIsAddEditOpen] = useState(false);

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
        fetchTeams();
    }, []);

    const toggleTeamExpansion = (teamCode: string) => {
        setExpandedTeams(prevState => ({
            ...prevState,
            [teamCode]: !prevState[teamCode]
        }));
    }

    const openAddEdit = () => {
        setIsAddEditOpen(true);
    };

    const closeAddEdit = () => {
        setIsAddEditOpen(false);
        fetchTeams(); // Refresh the team list after adding
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
                onClick={openAddEdit}
                className="mb-4 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            >
                Create New Team
            </button>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b"></th>
                            <th className="py-2 px-4 border-b">Team Name</th>
                            <th className="py-2 px-4 border-b">City</th>
                            <th className="py-2 px-4 border-b">Country</th>
                            <th className="py-2 px-4 border-b">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedTeams.map((team) => (
                            <React.Fragment key={team.teamCode}>
                                <tr>
                                    <td className="py-2 px-4 border-b">
                                        <button onClick={() => toggleTeamExpansion(team.teamCode)}>
                                            {expandedTeams[team.teamCode] ? <FaChevronDown /> : <FaChevronRight />}
                                        </button>
                                    </td>
                                    <td className="py-2 px-4 border-b">{team.name}</td>
                                    <td className="py-2 px-4 border-b">{team.city}</td>
                                    <td className="py-2 px-4 border-b">{team.country}</td>
                                    <td className="py-2 px-4 border-b">
                                        <button className="ml-2 px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors">
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                                <tr className={`transition-all duration-500 ease-in-out ${expandedTeams[team.teamCode] ? 'h-auto opacity-100' : 'h-0 opacity-0 overflow-hidden'}`}>
                                    <td colSpan={5}>
                                        <div className={`transition-all duration-500 ease-in-out ${expandedTeams[team.teamCode] ? 'max-h-[1000px]' : 'max-h-0 overflow-hidden'}`}>
                                            <table className="min-w-full bg-gray-100">
                                                <thead>
                                                    <tr>
                                                        <th className="py-2 px-4 border-b">Player Name</th>
                                                        <th className="py-2 px-4 border-b">Position</th>
                                                        <th className="py-2 px-4 border-b">Birth Date</th>
                                                        <th className="py-2 px-4 border-b">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {team.players.sort((a, b) => a.lastName.localeCompare(b.lastName)).map((player: PlayerResponse) => (
                                                        <tr key={player.playerCode}>
                                                            <td className="py-2 px-4 border-b">{player.firstName} {player.lastName}</td>
                                                            <td className="py-2 px-4 border-b">{player.position}</td>
                                                            <td className="py-2 px-4 border-b">{player.birthDate}</td>
                                                            <td className="py-2 px-4 border-b">{player.nationality}</td>
                                                            <td className="py-2 px-4 border-b">
                                                                <button className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition-colors">
                                                                    Edit
                                                                </button>
                                                                <button className="ml-2 px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors">
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
            {isAddEditOpen && <AddEditTeam onClose={closeAddEdit} />}
        </div>
    );
}

export default TeamCrud;