import React from "react";
import { Stack, Tabs } from "expo-router";
import { COLORSS } from "../constants/theme";
import SearchHeader from "../Component/SearchHeader";
import Header from "../Component/Header";
import CartHeader from "../Component/CartHeader";
import { GestureHandlerRootView } from "react-native-gesture-handler";

export default function ScreensLayout() {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <Stack
        screenOptions={{
          headerStyle: {
            backgroundColor: "#f4511e",
          },
          headerTintColor: "#fff",
          headerTitleStyle: {
            fontWeight: "bold",
          },
        }}
      >
        <Stack.Screen
          options={{
            headerShown: false,
            title: "Search",
            // https://reactnavigation.org/docs/headers#adjusting-header-styles
            headerStyle: { backgroundColor: "white" },
            headerTintColor: "#fff",

            headerTitleStyle: {
              fontWeight: "bold",
            },
            // header: (props) => <SearchHeader></SearchHeader>,
          }}
          name="Search"
        />
        <Stack.Screen
          options={{
            headerStyle: { backgroundColor: "white" },
            headerTintColor: "#fff",
            headerTitleStyle: {
              fontWeight: "bold",
            },
            header: (props) => <Header></Header>,
          }}
          name="ProductDescription"
        />
        <Stack.Screen
          options={{
            title: "Cart",
            header: (props) => <CartHeader title={"Your Cart"}></CartHeader>,
          }}
          name="Cart"
        />
        <Stack.Screen
          options={{
            title: "Checkout",
            header: (props) => <CartHeader title={"Checkout"}></CartHeader>,
          }}
          name="Checkout"
        />

        <Stack.Screen
          options={{
            title: "Edit Profile",
            header: (props) => <CartHeader title={"Edit Profile"}></CartHeader>,
          }}
          name="Editprofile"
        />

        <Stack.Screen
          options={{
            title: "Checkout",
            headerShown: false,
          }}
          name="MapSearch"
        />

        <Stack.Screen
          options={{
            title: "Orders",
            header: (props) => <CartHeader title={"Orders"}></CartHeader>,
          }}
          name="Orders"
        />
        <Stack.Screen
          options={{
            title: "Settings",
            header: (props) => <CartHeader title={"Settings"}></CartHeader>,
          }}
          name="Settings"
        />
        <Stack.Screen
          options={{
            title: "BillingAdress",
            header: (props) => (
              <CartHeader title={"BillingAddress"}></CartHeader>
            ),
          }}
          name="BillingAdress"
        />
        <Stack.Screen
          options={{
            title: "Chifa",
            header: (props) => <CartHeader title={"Chifa"}></CartHeader>,
          }}
          name="Chifa"
        />

        <Stack.Screen
          options={{
            title: "Order Detail",
            header: (props) => <CartHeader title={"Order Detail"}></CartHeader>,
          }}
          name="Orderdetails"
        />
      </Stack>
    </GestureHandlerRootView>
  );
}
