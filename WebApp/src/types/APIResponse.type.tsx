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

export type IdsResponse = {
  code: number;
  result: string[];
};

export type BooleanResponse = {
  code: number;
  result: boolean;
};
