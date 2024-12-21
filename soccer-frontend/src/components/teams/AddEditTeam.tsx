import React, { useState } from 'react';
import TeamService from '../../services/teamService';
import { TeamRequest } from '../../dto/TeamRequest';
import { TeamResponse } from '../../dto/TeamResponse';

interface AddEditTeamProps {
    onClose: () => void;
    team?: TeamResponse | null; // Make team optional
}

const AddEditTeam: React.FC<AddEditTeamProps> = ({ onClose, team }) => {
    const [name, setName] = useState(team?.name || '');
    const [city, setCity] = useState(team?.city || '');
    const [country, setCountry] = useState(team?.country || '');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const teamData: TeamRequest = { name, city, country };
        try {
            if (team) {
                await TeamService.updateTeam(team.teamCode, teamData);
            } else {
                await TeamService.createTeam(teamData);
            }
            onClose();
        } catch (err) {
            console.error('Failed to save team', err);
        }
    };

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-8 rounded-lg shadow-lg w-96">
                <h2 className="text-2xl font-bold mb-4">{team ? 'Edit Team' : 'Add Team'}</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700">Team Name</label>
                        <input 
                            type="text" 
                            value={name} 
                            onChange={(e) => setName(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">City</label>
                        <input 
                            type="text" 
                            value={city} 
                            onChange={(e) => setCity(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Country</label>
                        <input 
                            type="text" 
                            value={country} 
                            onChange={(e) => setCountry(e.target.value)} 
                            className="w-full px-3 py-2 border rounded"
                            required 
                        />
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

export default AddEditTeam;
