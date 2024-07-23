import { View, Text, StyleSheet, Image } from "react-native";
import React from "react";
import { ProductRespData } from "../client/types/responses/StockResponses";
import { TouchableOpacity } from "react-native";
import { router } from "expo-router";

export default function SearchproductComp({ item }: { item: ProductRespData }) {
  return (
    <View
      style={{
        flexDirection: "row",
        width: "75%",
      }}
    >
      <View style={styles.ViewImage}>
        <Image style={styles.Image} source={{ uri: item.picture }}></Image>
      </View>
      <View style={styles.TitleView}>
        <Text style={styles.Title}>{item.name}</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "90%",
    height: 70,
    flexDirection: "row",
    borderBottomColor: "lightgray",
    borderBottomWidth: 0.75,
    marginBottom: 5,
    marginTop: 5,
  },
  ViewImage: {
    width: "30%",

    borderRadius: 10,
  },
  Image: {
    height: "100%",
    width: "100%",
  },
  TitleView: {
    width: "70%",

    justifyContent: "center",
  },
  Title: {
    padding: 10,
    fontWeight: "bold",
    fontSize: 16,
  },
});
