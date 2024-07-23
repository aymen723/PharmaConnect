import { View, Text, TouchableOpacity, StyleSheet } from "react-native";
import React, { useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import Entypo from "@expo/vector-icons/Entypo";
import { router } from "expo-router";

export default function CartHeader({ title }: { title: string }) {
  return (
    <View style={{ flexDirection: "row" }}>
      <View style={[Gstyles.headerContainer, styles.center]}>
        <TouchableOpacity
          style={{
            width: "20%",
            justifyContent: "center",
            alignItems: "center",
          }}
          onPress={() => {
            router.back();
          }}
        >
          <Entypo name="chevron-left" size={30} color="black" />
        </TouchableOpacity>
        <View>
          <Text style={{ fontSize: 20, fontWeight: "bold" }}>{title}</Text>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  center: {
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-start",
    flexDirection: "row",
    backgroundColor: COLORSS.maingray,
  },
});
