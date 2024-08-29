export type APIResponse = {
  code: number;
  totalElement?: number;
  totalPage?: number;
  message?: string;
};

export type CountResponse = {
  code: number;
  result: number;
};
