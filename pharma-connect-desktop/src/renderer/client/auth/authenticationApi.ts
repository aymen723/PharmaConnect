import axios from "axios";
import { AUTH_SERVICE_V1_URL } from "../constants/urls";
import { AuthenticationResponse } from "../types/auth/authResponses";

export const refreshToken = (refreshToken: string) => {
  return axios<AuthenticationResponse>({
    method: "get",
    url: `${AUTH_SERVICE_V1_URL}/auth/refresh`,
    params: {
      refreshToken: refreshToken,
    },
  });
};
