import { PlayerResponse } from "../../dto/PlayerResponse";

function PlayerCard({player}: {player: PlayerResponse}) {
    return (
        <div 
            key={player.playerCode}
            className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 p-4 border border-gray-200"
        >
            <div className="flex justify-between items-start">
                <div className="space-y-1">
                    <h3 className="text-lg font-semibold text-gray-800">
                        {player.firstName} {player.lastName}
                    </h3>
                    <p className="text-gray-600 font-medium">
                        {player.position}
                    </p>
                </div>
                <div className="text-right text-sm text-gray-500">
                    <p className="flex items-center justify-end gap-1">
                        <span>🎂</span>
                        {new Date(player.birthDate).toLocaleDateString()}
                    </p>
                    <p className="flex items-center justify-end gap-1">
                        <span>🌍</span>
                        {player.nationality}
                    </p>
                </div>
            </div>
        </div>
    );
}

export default PlayerCard;