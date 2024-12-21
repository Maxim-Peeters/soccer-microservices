import React, { useEffect, useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { MatchResponse } from '../../dto/MatchResponse';
import MatchService from '../../services/matchService';
import { LoadingSpinner } from '../LoadingSpinner';
import AddEditMatch from './AddEditMatch';

const MatchCrud: React.FC = () => {
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [matches, setMatches] = useState<MatchResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isAddEditOpen, setIsAddEditOpen] = useState(false);
    const [selectedMatch, setSelectedMatch] = useState<MatchResponse | null>(null);

    const fetchMatches = async () => {
        try {
            setLoading(true);
            setError(null);
            const fetchedMatches = await MatchService.getAllMatches();
            setMatches(fetchedMatches);
        } catch (err) {
            setError('Failed to fetch matches');
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
        fetchMatches();
    }, [isAuthenticated, navigate]);

    const openAddEdit = (match?: MatchResponse) => {
        setSelectedMatch(match || null);
        setIsAddEditOpen(true);
    };

    const closeAddEdit = () => {
        setIsAddEditOpen(false);
        setSelectedMatch(null);
        fetchMatches(); // Refresh the match list after adding/editing
    };

    const handleDeleteMatch = async (matchCode: string) => {
        if (window.confirm('Are you sure you want to delete this match?')) {
            try {
                await MatchService.deleteMatch(matchCode);
                fetchMatches();
            } catch (err) {
                setError('Failed to delete match');
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
                    onClick={() => fetchMatches()}
                    className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                >
                    Retry
                </button>
            </div>
        );
    }

    if (matches.length === 0) {
        return (
            <div className="text-center py-8 text-gray-500">
                No matches available
            </div>
        );
    }

    const sortedMatches = [...matches].sort((a, b) => new Date(a.dateTime).getTime() - new Date(b.dateTime).getTime());

    return (
        <div className="container mx-auto px-4">
            <h1 className="text-4xl font-bold mb-8 text-center">Match Management</h1>
            <button 
                onClick={() => openAddEdit()}
                className="mb-4 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            >
                Create New Match
            </button>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b text-center">Home Team</th>
                            <th className="py-2 px-4 border-b text-center">Away Team</th>
                            <th className="py-2 px-4 border-b text-center">Date</th>
                            <th className="py-2 px-4 border-b text-center">Location</th>
                            <th className="py-2 px-4 border-b text-center">Home Score</th>
                            <th className="py-2 px-4 border-b text-center">Away Score</th>
                            <th className="py-2 px-4 border-b text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedMatches.map((match) => (
                            <tr key={match.matchCode}>
                                <td className="py-2 px-4 border-b text-center">{match.homeTeam.name}</td>
                                <td className="py-2 px-4 border-b text-center">{match.awayTeam.name}</td>
                                <td className="py-2 px-4 border-b text-center">{new Date(match.dateTime).toLocaleDateString()}</td>
                                <td className="py-2 px-4 border-b text-center">{match.location}</td>
                                <td className="py-2 px-4 border-b text-center">{match.homeTeamScore}</td>
                                <td className="py-2 px-4 border-b text-center">{match.awayTeamScore}</td>
                                <td className="py-2 px-4 border-b text-center">
                                    <button 
                                        onClick={() => openAddEdit(match)}
                                        className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition-colors"
                                    >
                                        Edit
                                    </button>
                                    <button 
                                        onClick={() => handleDeleteMatch(match.matchCode)}
                                        className="ml-2 px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {isAddEditOpen && <AddEditMatch onClose={closeAddEdit} match={selectedMatch} />}
        </div>
    );
}

export default MatchCrud;
