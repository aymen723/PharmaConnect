import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import {
  clearLocalAuthData,
  setLocalAuthData,
} from "../../utils/lib/localDataOperations";

export type TokenData = {
  token: string;
  expirationDate: string;
  creationDate: string;
};

export type AuthData = {
  accessToken: TokenData;
  refreshToken: TokenData;
  accountId: number;
};

export type AuthState = {
  isValidated: boolean;
  authData: AuthData | null;
};

const initialState: AuthState = {
  isValidated: false,
  authData: null,
};
const AuthSlice = createSlice({
  name: "AuthSlice",
  initialState: initialState,
  reducers: {
    setAuthData: (state, action: PayloadAction<AuthData>) => {
      setLocalAuthData(action.payload);
      state.authData = action.payload;
    },
    logout: (state) => {
      clearLocalAuthData();
      state.authData = null;
    },
    validate: (state) => {
      state.isValidated = true;
    },
  },
});

export const AuthReducer = AuthSlice.reducer;
export const AuthActions = AuthSlice.actions;
