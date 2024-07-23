import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import { Redirect, router } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { LoginResp, UserProfile } from "@/client/types/responses/authResponses";

export default function index() {
  const [User, setUser] = useState<UserProfile | null>();
  const [Token, setToken] = useState<string | null>();

  async function CheckUser() {
    console.log("zdz");
    try {
      const value = await AsyncStorage.getItem("@User");
      const token = await AsyncStorage.getItem("@token");
      console.log(value);
      console.log(token);

      if (value !== null && token !== null) {
        const object = JSON.parse(value as string);
        setUser(object);

        // router.replace("/(tabs)");
        return <Redirect href={"/(tabs)/index"} />;
      } else {
        setUser(null);

        // router.push("/(auth)");
        return <Redirect href={"/(auth)/Signin"} />;
      }
    } catch (error) {
      console.log("error in  ssss", error);
    }
  }

  useEffect(() => {
    CheckUser();
  }, []);
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
