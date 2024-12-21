import React, { useState, useEffect } from 'react';
import { MatchRequest } from '../../dto/MatchRequest';
import MatchService from '../../services/matchService';
import TeamService from '../../services/teamService';
import { TeamResponse } from '../../dto/TeamResponse';
import { MatchResponse } from '../../dto/MatchResponse';

interface AddEditMatchProps {
    onClose: () => void;
    match?: MatchResponse | null;
}

const AddEditMatch: React.FC<AddEditMatchProps> = ({ onClose, match }) => {
    // Initialize state directly from match prop
    const [homeTeamCode, setHomeTeamCode] = useState(match?.homeTeam.teamCode || '');
    const [awayTeamCode, setAwayTeamCode] = useState(match?.awayTeam.teamCode || '');
    const [dateTime, setDateTime] = useState(match?.dateTime || '');
    const [location, setLocation] = useState(match?.location || '');
    const [homeTeamScore, setHomeTeamScore] = useState(match?.homeTeamScore || 0);
    const [awayTeamScore, setAwayTeamScore] = useState(match?.awayTeamScore || 0);
    const [teams, setTeams] = useState<TeamResponse[]>([]);


    // Fetch teams in a separate useEffect
    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const fetchedTeams = await TeamService.getAllTeams();
                setTeams(fetchedTeams);
            } catch (err) {
                console.error('Failed to fetch teams:', err);
            }
        };
        fetchTeams();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const matchRequest: MatchRequest = {
            homeTeamCode,
            awayTeamCode,
            dateTime,
            location,
            homeTeamScore,
            awayTeamScore
        };

        try {
            if (match) {
                await MatchService.editMatch(match.matchCode, matchRequest);
            } else {
                await MatchService.addMatch(matchRequest);
            }
            onClose();
        } catch (err) {
            console.error('Failed to save match');
        }
    };

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-4">{match ? 'Edit Match' : 'Add Match'}</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700">Home Team</label>
                        <select
                            value={homeTeamCode}
                            onChange={(e) => setHomeTeamCode(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        >
                            <option value="" disabled>Select Home Team</option>
                            {teams.map((team) => (
                                <option 
                                    key={team.teamCode} 
                                    value={team.teamCode}
                                    selected={team.teamCode === homeTeamCode}
                                >
                                    {team.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Away Team</label>
                        <select
                            value={awayTeamCode}
                            onChange={(e) => setAwayTeamCode(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        >
                            <option value="" disabled>Select Away Team</option>
                            {teams.map((team) => (
                                <option key={team.teamCode} value={team.teamCode}>
                                    {team.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Date and Time</label>
                        <input
                            type="datetime-local"
                            value={dateTime}
                            onChange={(e) => setDateTime(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Location</label>
                        <input
                            type="text"
                            value={location}
                            onChange={(e) => setLocation(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Home Team Score</label>
                        <input
                            type="number"
                            value={homeTeamScore}
                            onChange={(e) => setHomeTeamScore(Number(e.target.value))}
                            className="w-full px-3 py-2 border rounded-lg"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Away Team Score</label>
                        <input
                            type="number"
                            value={awayTeamScore}
                            onChange={(e) => setAwayTeamScore(Number(e.target.value))}
                            className="w-full px-3 py-2 border rounded-lg"
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors mr-2"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                        >
                            Save
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddEditMatch;