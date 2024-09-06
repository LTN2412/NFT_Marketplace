import { APIResponse } from "./APIResponse.type";

export interface Account {
  username: string;
  password: string;
  email: string;
}

export interface Token {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
}

export type TokenResponse = APIResponse & {
  result: Token;
};

export type AccountResponse = APIResponse & {
  result: Account;
};
