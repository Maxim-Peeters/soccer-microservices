import axios from "axios";
import { MatchResponse } from "../dto/MatchResponse";
import Cookies from 'js-cookie';
import { MatchRequest } from "../dto/MatchRequest";

const baseUrl = "http://localhost:8084/matches";

export default class MatchService {
  static async getAllMatches(): Promise<MatchResponse[]> {
    const response = await axios.get<MatchResponse[]>(baseUrl);
    return response.data;
  }

  static async addMatch(matchRequest: MatchRequest): Promise<string> {
    const authToken = Cookies.get("auth_token");
    const response = await axios.post(`${baseUrl}/create`, matchRequest, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }

  static async editMatch(matchCode: string, matchRequest: MatchRequest): Promise<string> {
    const authToken = Cookies.get("auth_token");
    const response = await axios.put(`${baseUrl}/edit/${matchCode}`, matchRequest, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }

  static async deleteMatch(matchCode: string): Promise<string> {
    const authToken = Cookies.get("auth_token");
    const response = await axios.delete(`${baseUrl}/remove/${matchCode}`, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return response.data; // Assuming response data is a string message
  }
}
