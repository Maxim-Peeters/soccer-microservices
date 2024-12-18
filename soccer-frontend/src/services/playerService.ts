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
  static async getPlayerByPlayerCode(playerCode: string): Promise<PlayerResponse> {
    try {
      const response = await axios.get(`${baseUrl}/by-id/${playerCode}`);
      return response.data; // Assuming response data is a string message
    } catch (error) {
      throw new Error('Player not found'); // Throw an error if there's a failure
    }
  }

  static async addPlayer(playerRequest: PlayerRequest): Promise<string> {
    try {
      const authToken = Cookies.get("auth_token");
      const response = await axios.post(`${baseUrl}/create`, playerRequest, {
        headers: {
          Authorization: `Bearer ${authToken}`
        }
      });
      return response.data; // Assuming response data is a string message
    } catch (error) {
      throw new Error('Failed to add player'); // Throw an error if there's a failure
    }
  }

  static async editPlayer(playerCode: string, playerRequest: PlayerRequest): Promise<string> {
    try {
      const authToken = Cookies.get("auth_token");
      const response = await axios.put(`${baseUrl}/edit/${playerCode}`, playerRequest, {
        headers: {
          Authorization: `Bearer ${authToken}`
        }
      });
      return response.data; // Assuming response data is a string message
    } catch (error) {
      throw new Error('Failed to edit player'); // Throw an error if there's a failure
    }
  }

  static async deletePlayer(playerCode: string): Promise<string> {
    try {
      const authToken = Cookies.get("auth_token");
      const response = await axios.delete(`${baseUrl}/remove/${playerCode}`, {
        headers: {
          Authorization: `Bearer ${authToken}`
        }
      });
      return response.data; // Assuming response data is a string message
    } catch (error) {
      throw new Error('Failed to delete player'); // Throw an error if there's a failure
    }
  }
}

