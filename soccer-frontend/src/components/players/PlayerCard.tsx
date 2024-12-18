import { PlayerResponse } from "../../dto/PlayerResponse";

function PlayerCard({player}: {player: PlayerResponse}){
    return(
        <div key={player.playerCode}>
            {player.firstName}
            {player.lastName}
            {player.birthDate}
            {player.position}
            
        </div>
    )
}
export default PlayerCard;