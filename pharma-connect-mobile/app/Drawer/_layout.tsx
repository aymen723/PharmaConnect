import { View, Text } from "react-native";
import React from "react";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { Drawer } from "expo-router/drawer";

export default function DrawerLayout() {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <Drawer>
        {/* <Drawer.Screen
          name="index" // This is the name of the page and must match the url from root
          options={{
            drawerLabel: "Home",
            title: "overview",
          }}
        /> */}
        <Drawer.Screen
          name="Products" // This is the name of the page and must match the url from root
          options={{
            drawerLabel: "Products",
            title: "overview",
          }}
        />
      </Drawer>
    </GestureHandlerRootView>
  );
}
