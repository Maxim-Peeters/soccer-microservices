import axios from "axios";
import { TeamResponse } from "../dto/TeamResponse";
import Cookies from 'js-cookie';
import { TeamRequest } from "../dto/TeamRequest";

const baseUrl = "http://localhost:8084/teams";

export default class TeamService {
    static async getAllTeams(): Promise<TeamResponse[]> {
        const response = await axios.get<TeamResponse[]>(baseUrl);
        return response.data;
    }

    static async getTeamByTeamCode(teamCode: string): Promise<TeamResponse> {
        const response = await axios.get(`${baseUrl}/by-id/${teamCode}`);
        return response.data;
    }

    static async createTeam(team: TeamRequest): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.post<string>(`${baseUrl}/create`, team, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }

    static async updateTeam(teamCode: string, team: TeamRequest): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.put<string>(`${baseUrl}/edit/${teamCode}`, team, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }

    static async deleteTeam(teamCode: string): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.delete<string>(`${baseUrl}/delete/${teamCode}`, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }
}
