import { TeamResponse } from "./TeamResponse";

export interface MatchResponse {
    matchCode: string;
    homeTeam: TeamResponse;
    awayTeam: TeamResponse;
    dateTime: string; // Using string for LocalDateTime (ISO-8601 format)
    location: string;
    homeTeamScore: number;
    awayTeamScore: number;
  }