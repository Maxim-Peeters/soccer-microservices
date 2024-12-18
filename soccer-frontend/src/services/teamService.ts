import axios from "axios";
import { TeamResponse } from "../dto/TeamResponse";

const baseUrl = "http://localhost:8081/api/teams";


export default class TeamService{
    static async getAllTeams(): Promise<TeamResponse[]> {
        try{

        const response = await axios.get<TeamResponse[]>(baseUrl);

        return response.data;
        } catch (error) {
            throw new Error('Teams not found'); // Throw an error if there's a failure
          }
    }
    static async getTeamByTeamCode(teamCode: string): Promise<TeamResponse>{
    try{
        const response = await axios.get(`${baseUrl}/by-id/${teamCode}`)
        return response.data;
    } catch (error) {
        throw new Error('Team not found'); // Throw an error if there's a failure
      }

    }
}
