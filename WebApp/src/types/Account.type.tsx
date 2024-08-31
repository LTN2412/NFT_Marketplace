import { APIResponse } from "./APIResponse.type";

export interface Token {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
}

export type TokenResponse = APIResponse & {
  result: Token;
};
