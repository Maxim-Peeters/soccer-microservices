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
    static async createTeam(resu: TeamResponse): Promise<TeamResponse> {
}
