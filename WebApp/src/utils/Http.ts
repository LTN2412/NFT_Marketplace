import axios, { AxiosInstance } from "axios";

const urlAccount = "http://localhost:8081/identity";
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
    });
  }
}
export const httpAccount = new Http(urlAccount).instance;
export const httpElastic = new Http(urlElastic).instance;
export const httpUser = new Http(urlUser).instance;
export const httpNotification = new Http(urlNotification).instance;
