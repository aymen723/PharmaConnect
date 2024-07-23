import { View, Text, StyleSheet } from "react-native";
import React from "react";
import { Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";

export default function AuthLayout() {
  return (
    <Stack>
      <Stack.Screen name="Signin" options={{ headerShown: false }} />
    </Stack>
  );
}
