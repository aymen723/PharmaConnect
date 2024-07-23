import React, { useState } from "react";
import { Tabs } from "expo-router";
import AccountHeader from "../Component/AccountHeader";
import AntDesign from "@expo/vector-icons/AntDesign";
import Feather from "@expo/vector-icons/Feather";
import { COLORSS } from "../constants/theme";

export default function HomeLayout() {
  const [count, setCount] = useState(0);

  return (
    <Tabs
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused }) => {
          if (route.name == "Home") {
            return <AntDesign name="home" size={22} color="black" />;
          } else if (route.name == "Map") {
            return <Feather name="map" size={22} color="black" />;
          } else if (route.name == "Account") {
            return <Feather name="settings" size={22} color="black" />;
          }
        },

        tabBarActiveTintColor: COLORSS.Green,
        tabBarInactiveTintColor: "gray",
        headerShown: false,
      })}
      initialRouteName="Home"
    >
      <Tabs.Screen
        name="Home"
        options={{
          headerShown: false,
          headerStyle: { backgroundColor: "white" },
          headerTintColor: "#fff",
          headerTitleStyle: {
            fontWeight: "bold",
          },

          // header: (props) => <MapHeader onModalToggle={props}></MapHeader>,
        }}
      />
      <Tabs.Screen
        name="Map"
        options={{
          headerShown: false,
          headerStyle: { backgroundColor: "white" },
          headerTintColor: "#fff",
          headerTitleStyle: {
            fontWeight: "bold",
          },

          // header: (props) => <MapHeader onModalToggle={props}></MapHeader>,
        }}
      />
      <Tabs.Screen
        options={{
          headerShown: true,
          headerStyle: { backgroundColor: "white" },
          headerTintColor: "#fff",
          headerTitleStyle: {
            fontWeight: "bold",
          },
          header: (props) => <AccountHeader></AccountHeader>,
        }}
        name="Account"
      />
    </Tabs>
  );
}
