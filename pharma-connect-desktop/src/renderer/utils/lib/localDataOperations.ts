import { AuthData } from "../../redux/slices/AuthSlice";

export const setLocalAuthData = (data: AuthData) => {
  localStorage.setItem("auth-data", JSON.stringify(data));
  localStorage.setItem("access-token", data.accessToken.token);
};

export const getLocalAuthData = () => {
  try {
    const authData = JSON.parse(
      localStorage.getItem("auth-data") as string
    ) as AuthData;
    return authData;
  } catch {
    return null;
  }
};

export const clearLocalAuthData = () => {
  localStorage.removeItem("auth-data");
  localStorage.removeItem("access-token");
};

export const getLocalAccessToken = () => {
  return localStorage.getItem("access-token");
};
