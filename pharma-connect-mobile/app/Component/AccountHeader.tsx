import { View, Text, StyleSheet } from "react-native";
import React from "react";
import { COLORSS, Gstyles } from "../constants/theme";

export default function AccountHeader() {
  return (
    <View
      style={[Gstyles.headerContainer, { backgroundColor: COLORSS.maingray }]}
    >
      <View style={styles.container}>
        <Text style={styles.headertitle}>My Profile</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  headertitle: {
    fontSize: 23,
    fontWeight: "bold",
  },
  container: {
    width: "100%",
    paddingLeft: 20,
    height: "100%",
    justifyContent: "center",
    alignItems: "flex-start",
  },
});
