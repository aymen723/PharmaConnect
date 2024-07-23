import { Text, StyleSheet, TouchableOpacity, View } from "react-native";
import React, { useEffect, useState } from "react";
import { TagRespData } from "../client/types/responses/StockResponses";
import { COLORSS, Tagcolors } from "../constants/theme";

export default function Tag({ item }: { item: TagRespData }) {
  const [Color, setColor] = useState(
    Tagcolors.find((o) => o.name == item.name)?.color
  );

  return (
    <View style={styles.container}>
      <Text style={[styles.TagText, { color: Color }]}>{item.name}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: COLORSS.maingray,
    padding: 10,
    paddingHorizontal: 15,
    borderRadius: 15,
    marginLeft: 5,
    marginRight: 5,
  },
  TagText: {
    fontSize: 16,
    fontWeight: "bold",
    // color: "white",
  },
});
