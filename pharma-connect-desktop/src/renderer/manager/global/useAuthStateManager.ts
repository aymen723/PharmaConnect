import React from "react";
import { useAppSelector } from "../../redux/hooks/useAppSelector";
import { useAppDispatch } from "../../redux/hooks/useAppDispatch";
import { getLocalAuthData } from "../../utils/lib/localDataOperations";
import { AuthActions } from "../../redux/slices/AuthSlice";
import dayjs from "dayjs";
import { refreshToken } from "../../client/auth/authenticationApi";
export const useAuthStateManager = () => {
  const authData = useAppSelector((app) => app.auth.authData);
  const isAuthValidated = useAppSelector((app) => app.auth.isValidated);
  const dispatch = useAppDispatch();
  React.useEffect(() => {
    if (!authData && !isAuthValidated) {
      const localAuthData = getLocalAuthData();
      if (localAuthData) {
        const now = dayjs();
        const refreshExpDate = dayjs(localAuthData.accessToken.expirationDate);

        if (
          now.isAfter(refreshExpDate) ||
          now.diff(refreshExpDate, "minutes") > 5
        ) {
          refreshToken(localAuthData.refreshToken.token)
            .then((res) => {
              dispatch(
                AuthActions.setAuthData({
                  accessToken: res.data.tokenData,
                  refreshToken: res.data.refreshTokenData,
                  accountId: res.data.accountId,
                })
              );
            })
            .finally(() => {
              dispatch(AuthActions.validate());
            });
        } else {
          dispatch(AuthActions.validate());
        }
      }
    }
  }, [authData, isAuthValidated, dispatch]);
};
