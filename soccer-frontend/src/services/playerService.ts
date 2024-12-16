import axios from "axios";
import { PlayerResponse } from "../dto/PlayerResponse";

const baseUrl = "http://localhost:8080/api/players";

export default class PlayerService {
  static async get(token: string): Promise<PlayerResponse[]> {
    const response = await axios.get<PlayerResponse[]>(baseUrl, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  }
}

