import { PlayerResponse } from "../../dto/PlayerResponse";

function PlayerCardComponent({player}: {player: PlayerResponse}){
    return(
        <div key={player.playerCode}>
            {player.firstName}
            {player.lastName}
            {player.birthDate}
            {player.position}
            
        </div>
    )
}
export default PlayerCardComponent;