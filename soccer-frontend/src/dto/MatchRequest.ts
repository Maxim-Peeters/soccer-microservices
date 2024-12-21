export interface MatchRequest {
    homeTeamCode: string;     // Can be team ID or name
    awayTeamCode: string;     // Can be team ID or name
    dateTime: string;           // TypeScript uses Date object for datetime
    location: string;
    homeTeamScore: number;   // Made optional with '?' as scores might not be known initially
    awayTeamScore: number;   // Made optional with '?' as scores might not be known initially
}