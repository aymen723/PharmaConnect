import { View, Text } from "react-native";
import React from "react";
import { Stack } from "expo-router";

export default function ScreensLayout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
      }}
    >
      <Stack.Screen name="Camera" options={{ headerShown: false }} />
      <Stack.Screen name="Orderdetail" options={{ headerShown: false }} />
    </Stack>
  );
}
