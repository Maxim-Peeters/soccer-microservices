import React, { useState, useEffect } from 'react';
import { SupporterRequest } from '../../dto/SupporterRequest';
import { SupporterResponse } from '../../dto/SupporterResponse';
import { TeamResponse } from '../../dto/TeamResponse';
import { PlayerResponse } from '../../dto/PlayerResponse';
import SupporterService from '../../services/supporterService';
import TeamService from '../../services/teamService';
import PlayerService from '../../services/playerService';

interface AddEditSupporterProps {
    onClose: () => void;
    supporter?: SupporterResponse | null;
}

const AddEditSupporter: React.FC<AddEditSupporterProps> = ({ onClose, supporter }) => {
    const [firstName, setFirstName] = useState(supporter?.firstName || '');
    const [lastName, setLastName] = useState(supporter?.lastName || '');
    const [email, setEmail] = useState(supporter?.email || '');
    const [birthDate, setBirthDate] = useState(supporter?.birthDate || '');
    const [teamCode, setTeamCode] = useState(supporter?.teamName.teamCode || '');
    const [playerCode, setPlayerCode] = useState(supporter?.favoritePlayer.playerCode || '');
    
    const [teams, setTeams] = useState<TeamResponse[]>([]);
    const [players, setPlayers] = useState<PlayerResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [fetchedTeams, fetchedPlayers] = await Promise.all([
                    TeamService.getAllTeams(),
                    PlayerService.getAllPlayers()
                ]);
                setTeams(fetchedTeams);
                setPlayers(fetchedPlayers);
            } catch (err) {
                setError('Failed to fetch required data');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const supporterRequest: SupporterRequest = {
            firstName,
            lastName,
            email,
            birthDate,
            teamCode,
            playerCode
        };

        try {
            if (supporter) {
                await SupporterService.editSupporter(supporter.supporterCode, supporterRequest);
            } else {
                await SupporterService.addSupporter(supporterRequest);
            }
            onClose();
        } catch (err) {
            setError('Failed to save supporter');
        }
    };

    if (loading) {
        return (
            <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
                <div className="bg-white p-8 rounded-lg">Loading...</div>
            </div>
        );
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-4">{supporter ? 'Edit Supporter' : 'Add Supporter'}</h2>
                {error && <p className="text-red-500 mb-4">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700">First Name</label>
                        <input
                            type="text"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Last Name</label>
                        <input
                            type="text"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Birth Date</label>
                        <input
                            type="date"
                            value={birthDate}
                            onChange={(e) => setBirthDate(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Favorite Team</label>
                        <select
                            value={teamCode}
                            onChange={(e) => setTeamCode(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        >
                            <option value="">Select Team</option>
                            {teams.map((team) => (
                                <option key={team.teamCode} value={team.teamCode}>
                                    {team.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Favorite Player</label>
                        <select
                            value={playerCode}
                            onChange={(e) => setPlayerCode(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg"
                            required
                        >
                            <option value="">Select Player</option>
                            {players.map((player) => (
                                <option key={player.playerCode} value={player.playerCode}>
                                    {player.firstName} {player.lastName}
                                </option>
                            ))}
                        </select>
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

export default AddEditSupporter;
