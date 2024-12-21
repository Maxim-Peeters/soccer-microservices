import React, { useState, useEffect } from 'react';
import PlayerService from '../../services/playerService';
import { PlayerRequest } from '../../dto/PlayerRequest';
import { PlayerResponse } from '../../dto/PlayerResponse';
import TeamService from '../../services/teamService';
import { TeamResponse } from '../../dto/TeamResponse';

interface AddEditPlayerProps {
    onClose: () => void;
    player?: PlayerResponse | null; // Make player optional
    teamCode: string;
}

const AddEditPlayer: React.FC<AddEditPlayerProps> = ({ onClose, player, teamCode: initialTeamCode }) => {
    const [firstName, setFirstName] = useState(player?.firstName || '');
    const [lastName, setLastName] = useState(player?.lastName || '');
    const [position, setPosition] = useState(player?.position || '');
    const [birthDate, setBirthDate] = useState(player?.birthDate || '');
    const [nationality, setNationality] = useState(player?.nationality || '');
    const [teamCode, setTeamCode] = useState(initialTeamCode);
    const [teams, setTeams] = useState<TeamResponse[]>([]);


    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const teams = await TeamService.getAllTeams();
                setTeams(teams);
            } catch (err) {
                console.error('Failed to fetch teams', err);
            }
        };

        fetchTeams();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const playerData: PlayerRequest = {
            firstName,
            lastName,
            position,
            teamCode,
            birthDate,
            nationality
        };

        try {
            if (player && player.playerCode) {
                await PlayerService.editPlayer(player.playerCode, playerData);
            } else {
                await PlayerService.addPlayer(playerData);
            }
            onClose();
        } catch (err) {
            console.error('Failed to save player', err);
        }
    };
    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-8 rounded-lg shadow-lg w-96">
                <h2 className="text-2xl font-bold mb-4">
                    {player ? 'Edit Player' : 'Add Player'}
                </h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700">First Name</label>
                        <input 
                            type="text" 
                            value={firstName} 
                            onChange={(e) => setFirstName(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Last Name</label>
                        <input 
                            type="text" 
                            value={lastName} 
                            onChange={(e) => setLastName(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Position</label>
                        <select
                            value={position}
                            onChange={(e) => setPosition(e.target.value)}
                            className="w-full px-3 py-2 border rounded"
                            required
                        >
                            <option value="" disabled>Select Position</option>

                            <option value="Goalkeeper">Goalkeeper</option>
                            <option value="Defender">Defender</option>
                            <option value="Midfielder">Midfielder</option>
                            <option value="Forward">Forward</option>
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Birth Date</label>
                        <input 
                            type="date" 
                            value={birthDate} 
                            onChange={(e) => setBirthDate(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    <div className="mb-4">
                        <label className="block text-gray-700">Nationality</label>
                        <input 
                            type="text" 
                            value={nationality} 
                            onChange={(e) => setNationality(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Team</label>
                        <select
                            value={teamCode}
                            onChange={(e) => setTeamCode(e.target.value)}
                            className="w-full px-3 py-2 border rounded"
                            required
                        >
                            <option value="" disabled>Select Team</option>
                            {teams.map((team) => (
                                <option key={team.teamCode} value={team.teamCode} selected={team.teamCode === teamCode}>
                                    {team.name} - {team.teamCode} - {initialTeamCode}
                                </option>
                            ))}
                        </select>
                    </div>
                    </div>
                    <div className="flex justify-end">
                        <button 
                            type="button" 
                            onClick={onClose} 
                            className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 transition-colors mr-2"
                        >
                            Cancel
                        </button>
                        <button 
                            type="submit" 
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-colors"
                        >
                            Save
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddEditPlayer;