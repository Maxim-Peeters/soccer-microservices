import { PlayerResponse } from "./PlayerResponse";

export interface TeamResponse {
    teamCode: string;
    name: string;
    city: string;
    country: string;
    players: PlayerResponse[];
}