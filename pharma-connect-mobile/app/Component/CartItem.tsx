import { View, Text, StyleSheet, Image, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import AntDesign from "@expo/vector-icons/AntDesign";
import { COLORSS } from "../constants/theme";
import { ProductRespData } from "../client/types/responses/StockResponses";
import { CartItemtype, useCartStore } from "../zustand/store";

export default function CartItem({ item }: { item: CartItemtype }) {
  const { deleteitem, increment, decrement } = useCartStore();

  return (
    <View style={styles.itemcontainer}>
      <View style={styles.picontainer}>
        <Image
          style={styles.itempic}
          source={{ uri: item.product.picture }}
        ></Image>
      </View>
      <View style={styles.infoitem}>
        <View style={styles.itemtitle}>
          <View style={[styles.titlesection, { paddingLeft: 10 }]}>
            <Text style={styles.infotext}>{item.product.name}</Text>
          </View>
          <TouchableOpacity
            style={{
              justifyContent: "center",
              alignItems: "center",
            }}
            onPress={() => {
              deleteitem(item);
            }}
          >
            <AntDesign
              name="closecircleo"
              color={"rgba(0,0,0,0.25)"}
              size={20}
            />
          </TouchableOpacity>
        </View>
        <View style={[styles.itemtitle, { height: "40%" }]}>
          <View
            style={[styles.titlesection, { width: "50%", paddingLeft: 10 }]}
          >
            <Text
              style={[styles.infotext, { fontSize: 16, color: COLORSS.Green }]}
            >
              DA {item.product.price}
            </Text>
          </View>
          <View
            style={{
              flexDirection: "row",
              width: "50%",
              justifyContent: "space-evenly",
              alignItems: "center",
            }}
          >
            <TouchableOpacity
              style={{
                borderRadius: 20,
                width: 20,
                height: 20,
                // backgroundColor: "lightred",
                justifyContent: "center",
                alignItems: "center",
              }}
              onPress={() => {
                decrement(item.product.id);
              }}
            >
              <AntDesign name="minussquare" size={20} color="#da1e37" />
            </TouchableOpacity>
            <View>
              <Text style={{ fontWeight: "bold", fontSize: 14 }}>
                {item.count}
              </Text>
            </View>
            <TouchableOpacity
              style={{
                borderRadius: 20,
                width: 20,
                height: 20,
                // backgroundColor: COLORSS.Green,
                justifyContent: "center",
                alignItems: "center",
              }}
              onPress={() => {
                increment(item.product.id);
              }}
            >
              <AntDesign name="plussquare" size={20} color={COLORSS.Green} />
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  itemcontainer: {
    width: "95%",
    height: 100,
    flexDirection: "row",
    borderBottomColor: "lightgray",
    borderBottomWidth: 0.75,
    marginBottom: 5,
    marginTop: 5,
  },
  itempic: {
    width: "100%",
    height: "100%",
    borderRadius: 10,
    resizeMode: "stretch",
  },
  picontainer: {
    width: "30%",
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
    // borderWidth: 1,
    // borderColor: "red",
  },
  infoitem: {
    width: "70%",
    height: "100%",
    // borderWidth: 1,
    // borderColor: "green",
  },
  itemtitle: {
    width: "100%",
    height: "60%",

    flexDirection: "row",
  },
  titlesection: {
    width: "90%",
    height: "100%",
    // borderColor: "red",
    // borderWidth: 1,
    justifyContent: "center",
    alignItems: "flex-start",
  },
  infotext: {
    color: COLORSS.textcolor,
    fontWeight: "600",
    fontSize: 17,
    lineHeight: 18,
  },
});
