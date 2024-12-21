import React, { useState, useEffect } from 'react';
import { TeamResponse } from '../dto/TeamResponse';
import TeamService from '../services/teamService';
import { LoadingSpinner } from '../components/LoadingSpinner';
import TeamCard from '../components/teams/TeamCard';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
const TeamList: React.FC = () => {
    const {isAuthenticated} = useAuth();
    const [teams, setTeams] = useState<TeamResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

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
    };

    useEffect(() => {
        fetchTeams();
    }, []);

    if (loading) {
            return(
            <LoadingSpinner/>
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

    return (
      
        <div className="container mx-auto px-4 py-8">
                        <h1 className="text-4xl font-bold mb-4 text-center">Teams</h1>
            <div className="flex justify-center mb-4">
              {isAuthenticated &&(<button
                onClick={() => navigate('/manage-teams')}
                className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors">
                Manage Teams
              </button>)}
            </div>
            <div className="grid grid-cols-1 xl:grid-cols-2 gap-8">
                {teams.map(team => (
                    <TeamCard key={team.teamCode} team={team} />
                ))}
            </div>
        </div>
    );
}

export default TeamList;