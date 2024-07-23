import { Stack } from "expo-router";
import React from "react";

export default function StackLayout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
      }}
      initialRouteName="Loading"
    >
      <Stack.Screen name="Onboarding" />
      <Stack.Screen name="Signin" />
    </Stack>
  );
}
