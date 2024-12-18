import { PlayerResponse } from "./PlayerResponse";
import { TeamResponse } from "./TeamResponse";
export interface SupporterResponse {
    supporterCode: string;
    firstName: string;
    lastName: string;
    birthDate: string; // Using string for LocalDate (ISO-8601 format)
    email: string;
    teamName: TeamResponse;
    favoritePlayer: PlayerResponse;
  }