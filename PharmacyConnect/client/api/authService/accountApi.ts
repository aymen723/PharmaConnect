import axios from "axios";
import { AUTH_SERVICE_URL_V1 } from "../../config/settings";
import { UserProfile } from "../../types/responses/authResponses";
import { getLocalAccessToken } from "@/Hooks/token";

export const fetchAccountProfile = async (accountId: number) => {
  const token = await getLocalAccessToken();

  return axios<UserProfile>({
    url: `${AUTH_SERVICE_URL_V1}/accounts/${accountId}/profile`,
    method: "get",
    headers: {
      Authorization: `Bearer ` + token,
    },
  });
};

export const changePassword = (pwd: string) => {
  return axios<UserProfile>({
    url: `${AUTH_SERVICE_URL_V1}/accounts/pwd`,
    method: "patch",
    headers: {
      Authorization: `Bearer ` + getLocalAccessToken(),
    },
    data: {
      newPassword: pwd,
    },
  });
};

type lol = Awaited<ReturnType<typeof fetchAccountProfile>>["data"];
