import React from 'react';
import { MatchResponse } from '../../dto/MatchResponse';

interface MatchCardProps {
    match: MatchResponse;
}

const MatchCard: React.FC<MatchCardProps> = ({ match }) => {
    const matchTime = new Date(match.dateTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    return (
        <div className="border rounded-lg p-4 shadow-md">
            <div className="flex justify-between items-center mb-2">
                <h2 className="text-xl font-bold">{match.homeTeam.name} vs {match.awayTeam.name}</h2>
                <span className="text-gray-500">{matchTime}</span>
            </div>
            <div className="flex justify-between items-center mb-2">
                <span className="text-lg">{match.location}</span>
                <span className="text-lg font-bold">{match.homeTeamScore} - {match.awayTeamScore}</span>
            </div>
        </div>
    );
}

export default MatchCard;
