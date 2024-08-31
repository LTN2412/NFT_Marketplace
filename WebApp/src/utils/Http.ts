import axios, { AxiosInstance } from "axios";
import Cookies from "js-cookie";

const urlAccount = "http://localhost:8081";
const urlElastic = "http://localhost:8083/elastic";
const urlUser = "http://localhost:8084/user";
const urlNotification = "http://localhost:8085/notification";

class Http {
  instance: AxiosInstance;
  constructor(url: string) {
    this.instance = axios.create({
      baseURL: url,
      timeout: 10000,
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
    this.instance.interceptors.request.use(async (config) => {
      const accessToken = Cookies.get("accessToken");
      if (accessToken) {
        config.headers["Authorization"] = `Bearer ${accessToken}`;
      }
      return config;
    });
  }
}
export const httpAccount = new Http(urlAccount).instance;

export const httpElastic = new Http(urlElastic).instance;

export const httpUser = new Http(urlUser).instance;

export const httpNotification = new Http(urlNotification).instance;
