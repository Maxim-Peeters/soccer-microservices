import React, { useState, useEffect } from 'react';
import { MatchResponse } from '../dto/MatchResponse';
import MatchService from '../services/matchService';
import { LoadingSpinner } from '../components/LoadingSpinner';
import MatchCard from '../components/matches/MatchCard';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
const MatchesPage: React.FC = () => {
    const {isAuthenticated} = useAuth();
    const [matches, setMatches] = useState<MatchResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

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
    };

    useEffect(() => {
        fetchMatches();
    }, []);

    if (loading) {
        return <LoadingSpinner />;
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

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-4xl font-bold mb-4 text-center">Matches</h1>
            <div className="flex justify-center mb-4">
              {isAuthenticated && (
                <button
                  onClick={() => navigate('/manage-matches')}
                  className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors">
                  Manage Matches
                </button>
              )}
            </div>
            <div className="grid grid-cols-1 xl:grid-cols-2 gap-8">
                {matches.map(match => (
                    <MatchCard key={match.matchCode} match={match} />
                ))}
            </div>
        </div>
    );
}

export default MatchesPage;