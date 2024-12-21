export interface PlayerRequest {
    firstName: string;
    lastName: string;
    position: string;
    teamCode: string;
    birthDate: string; // You can use string for date representation (ISO format: YYYY-MM-DD)
    nationality: string;
}