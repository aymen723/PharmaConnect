// export const eraseLocalAuthState = () => {
//   AsyncStorage.removeItem("authData");
//   AsyncStorage.removeItem("app-access-token");
// };

import AsyncStorage from "@react-native-async-storage/async-storage";

export const getLocalAccessToken = async () => {
  try {
    const value = await AsyncStorage.getItem("@token");
    if (value !== null) {
      console.log(JSON.parse(value));
      const tokendata = JSON.parse(value);

      return tokendata.token;
    }
  } catch (error) {
    console.log(error);
    return error;
  }
};
