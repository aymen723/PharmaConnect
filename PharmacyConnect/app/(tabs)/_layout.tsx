import React from "react";
import FontAwesome from "@expo/vector-icons/FontAwesome";
import { Link, Tabs } from "expo-router";
import { Pressable } from "react-native";

import Colors from "@/constants/Colors";
import { useColorScheme } from "@/components/useColorScheme";
import { useClientOnlyValue } from "@/components/useClientOnlyValue";
import { COLORSS } from "@/constants/theme";
import { StatusBar } from "expo-status-bar";
import { Feather } from "@expo/vector-icons";
import { Entypo } from "@expo/vector-icons";
function TabBarIcon(props: {
  name: React.ComponentProps<typeof FontAwesome>["name"];
  color: string;
}) {
  return <FontAwesome size={28} style={{ marginBottom: -3 }} {...props} />;
}

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <>
      <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>
      <Tabs
        screenOptions={{
          tabBarActiveTintColor: Colors[colorScheme ?? "light"].tint,

          // Disable the static render of the header on web
          // to prevent a hydration error in React Navigation v6.

          // headerShown: useClientOnlyValue(false, true),
          headerShown: false,
        }}
      >
        <Tabs.Screen
          name="index"
          options={{
            title: "Stock",
            tabBarIcon: ({ color }) => (
              <Entypo name="add-to-list" size={24} color={color} />
            ),
            headerShown: false,
          }}
        />
        <Tabs.Screen
          name="two"
          options={{
            headerShown: false,

            title: "Les Achats",
            tabBarIcon: ({ color }) => (
              <Feather name="shopping-bag" size={24} color={color} />
            ),
          }}
        />
        <Tabs.Screen
          name="Profile"
          options={{
            title: "Profile",
            tabBarIcon: ({ color }) => (
              <Feather name="user" size={24} color={color} />
            ),
          }}
        />
      </Tabs>
    </>
  );
}
