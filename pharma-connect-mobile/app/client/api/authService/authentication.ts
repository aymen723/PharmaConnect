import axios from "axios";
import { AUTH_SERVICE_URL_V1 } from "../../config/settings";
import { LoginResp } from "../../types/responses/authResponses";
import { CredentialLoginRequest } from "../../types/requests/authRequests";

export const googleAccessAuthentication = (accessToken: string) => {
  return axios<LoginResp>({
    url: `${AUTH_SERVICE_URL_V1}/auth/google`,
    method: "post",
    data: {
      accessToken,
      provider: "GOOGLE",
    },
  });
};

export const googleIdAuthentication = (idToken: string) => {
  return axios<LoginResp>({
    url: `${AUTH_SERVICE_URL_V1}/auth/google`,
    method: "post",
    data: {
      idToken: idToken,
      provider: "GOOGLE",
    },
  });
};

export const credentialAuthentication = (request: CredentialLoginRequest) => {
  return axios<LoginResp>({
    url: `${AUTH_SERVICE_URL_V1}/auth/login`,
    method: "post",
    data: request,
  });
};
