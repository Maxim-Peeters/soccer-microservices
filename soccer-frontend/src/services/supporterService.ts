import axios from "axios";
import { SupporterResponse } from "../dto/SupporterResponse";
import { SupporterRequest } from "../dto/SupporterRequest";
import Cookies from 'js-cookie';

const baseUrl = "http://localhost:8084/supporters";

export default class SupporterService {
    static async getAllSupporters(): Promise<SupporterResponse[]> {
        const response = await axios.get<SupporterResponse[]>(baseUrl);
        return response.data;
    }

    static async addSupporter(supporterRequest: SupporterRequest): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.post(`${baseUrl}/create`, supporterRequest, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }

    static async editSupporter(supporterCode: string, supporterRequest: SupporterRequest): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.put(`${baseUrl}/edit/${supporterCode}`, supporterRequest, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }

    static async deleteSupporter(supporterCode: string): Promise<string> {
        const authToken = Cookies.get("auth_token");
        const response = await axios.delete(`${baseUrl}/remove/${supporterCode}`, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return response.data;
    }
}
