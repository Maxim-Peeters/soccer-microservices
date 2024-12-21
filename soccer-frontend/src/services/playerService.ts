import axios from "axios";
import { PlayerResponse } from "../dto/PlayerResponse";
import Cookies from 'js-cookie';
import { PlayerRequest } from "../dto/PlayerRequest";

const baseUrl = "http://localhost:8084/players";

export default class PlayerService {
  static async getAllPlayers(): Promise<PlayerResponse[]> {
    const response = await axios.get<PlayerResponse[]>(baseUrl);
    return response.data;
  }
  static async addPlayer(playerRequest: PlayerRequest): Promise<string> {
    const authToken = Cookies.get("auth_token");
    console.log("jaja");
    const response = await axios.post(`${baseUrl}/create`, playerRequest, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }

  static async editPlayer(playerCode: string, playerRequest: PlayerRequest): Promise<string> {
    const authToken = Cookies.get("auth_token");
    const response = await axios.put(`${baseUrl}/edit/${playerCode}`, playerRequest, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }

  static async deletePlayer(playerCode: string): Promise<string> {
    const authToken = Cookies.get("auth_token");
    const response = await axios.delete(`${baseUrl}/remove/${playerCode}`, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }
}
