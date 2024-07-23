// import React, { useEffect, useState } from "react";
// import { Redirect, Slot } from "expo-router";
// import AsyncStorage from "@react-native-async-storage/async-storage";

// export default function App() {
//   return <Slot></Slot>;
// }

import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Redirect, router } from "expo-router";
import { UserType } from "./client/types/responses/Client";

export default function Loading() {
  const [User, setUser] = useState<UserType | null>();
  const [Token, setToken] = useState<string | null>();

  async function CheckUser() {
    try {
      const value = await AsyncStorage.getItem("@User");
      const token = await AsyncStorage.getItem("@token");
      router.push("/(auth)/Onboarding");
      console.log(value);
      console.log(token);

      if (value !== null && token !== null) {
        const object = JSON.parse(value as string);
        router.replace("/tabs/Home");
        // return <Redirect href={"/src/tabs"} />;
      } else {
        router.push("/(auth)/Onboarding");
        // return <Redirect href={"/src/tabs/Onboarding"} />;
      }
    } catch (error) {
      console.log("error in Loading Screen", error);
    }
  }

  useEffect(() => {
    CheckUser();
  }, [User]);
  return (
    <View
      style={{
        flex: 1,
        backgroundColor: "white",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      {/* {CheckUser()} */}
    </View>
  );
}
