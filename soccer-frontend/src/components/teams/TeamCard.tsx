import { TeamResponse } from "../../dto/TeamResponse";
import PlayerCard from "../players/PlayerCard";

const positions = ['Forward', 'Midfielder', 'Defender', 'Goalkeeper'];

function TeamCard({ team }: { team: TeamResponse }) {
  return (
    <div className="team-card bg-white shadow-lg rounded-xl p-8 hover:shadow-xl transition-shadow duration-300 border border-gray-200 w-full max-w-4xl mx-auto">
      <div className="border-b border-gray-200 pb-4 mb-6">
        <h2 className="text-4xl font-bold mb-3 text-gray-800">{team.name}</h2>
        <p className="text-gray-600 text-xl">
          <span className="inline-block mr-2">📍</span>
          {team.city}, {team.country}
        </p>
      </div>
      
      <div className="space-y-6">
        {positions.map(position => (
          <div key={position}>
            <h3 className="text-2xl font-semibold text-gray-800 mb-4">{position}s</h3>
            <div className="grid gap-4">
              {team.players
                .filter(player => player.position === position)
                .map(player => (
                  <PlayerCard key={player.playerCode} player={player} />
                ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TeamCard;