import React from "react";
import { View, Text, StyleSheet, Dimensions, Image } from "react-native";
import type { walk } from "../Models/walkthrough";

const { width, height } = Dimensions.get("window");
export default function Walkthrough(item: walk) {
  return (
    <View style={styles.container}>
      <View style={styles.box1}>
        <Image
          source={item.image}
          style={{ height: "100%", width, resizeMode: "contain" }}
        ></Image>
      </View>
      <View style={styles.box2}>
        <View style={styles.titlebox}>
          <Text style={styles.title}>{item.title}</Text>
        </View>
        <View style={styles.textbox}>
          <Text style={styles.text}>{item.text}</Text>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    // borderWidth: 1,
    // borderColor: "red",
    width: width,
    display: "flex",
    justifyContent: "center",
  },
  box1: {
    // width: "100%",
    // borderWidth: 1,
    borderColor: "green",
    height: "60%",
  },
  box2: {
    width: "85%",
    // borderWidth: 1,
    // borderColor: "green",
    height: "40%",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },

  titlebox: {
    height: "50%",
    width: "100%",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  textbox: {
    height: "50%",
    width: "100%",
    display: "flex",
    justifyContent: "flex-start",
  },
  title: {
    fontWeight: "bold",
    fontSize: 24,
  },
  text: {
    fontSize: 14,
    color: "gray",
    textAlign: "center",
  },
});
