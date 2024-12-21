import React from 'react';
import { SupporterResponse } from '../../dto/SupporterResponse';

interface SupporterCardProps {
    supporter: SupporterResponse;
}

const SupporterCard: React.FC<SupporterCardProps> = ({ supporter }) => {
    return (
        <div className="border rounded-lg p-4 shadow-md">
            <h2 className="text-xl font-bold mb-2">{supporter.firstName} {supporter.lastName}</h2>
            <div className="text-gray-600">
                <p>Email: {supporter.email}</p>
                <p>Birth Date: {new Date(supporter.birthDate).toLocaleDateString()}</p>
                <p>Favorite Team: {supporter.teamName.name}</p>
                <p>Favorite Player: {supporter.favoritePlayer.firstName} {supporter.favoritePlayer.lastName}</p>
            </div>
        </div>
    );
}

export default SupporterCard;
