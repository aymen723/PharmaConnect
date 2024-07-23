import { TokenData } from "../../../redux/slices/AuthSlice";

export type AuthenticationResponse = {
  tokenData: TokenData;
  refreshTokenData: TokenData;
  accountId: number;
  role: "ADMIN" | "CLIENT" | "PHARMACY";
};
