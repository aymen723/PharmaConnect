import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  ScrollView,
} from "react-native";
import React, { useState } from "react";
import { router } from "expo-router";
import { ProductRespData } from "../client/types/responses/StockResponses";
import { SHADOWS, COLORSS } from "../constants/theme";
import { Feather } from "@expo/vector-icons";
import MaterialCommunityIcons from "@expo/vector-icons/MaterialCommunityIcons";
export default function Productcard({ item }: { item: ProductRespData }) {
  const [Booked, setBooked] = useState(true);

  return (
    <TouchableOpacity
      onLongPress={() => {
        setBooked(!Booked);
      }}
      key={item.id}
      onPress={() => {
        router.push({
          pathname: `/Screens/ProductDescription`,
          params: {
            id: item.id,
            name: item.name,
          },
        });
      }}
      activeOpacity={0.8}
      style={[styles.conatiner, { ...SHADOWS.small }]}
    >
      <View style={styles.imagebox}>
        <Image source={{ uri: item.picture }} style={styles.image}></Image>
      </View>
      <View style={styles.contentbox}>
        <View style={styles.title}>
          <Text
            style={{
              fontSize: 15,
              fontWeight: "bold",
              color: COLORSS.textcolor,
            }}
          >
            {item.name}
          </Text>
          <View style={{ flexDirection: "row", alignItems: "center" }}>
            <Text
              style={{
                fontSize: 12,
                fontWeight: "700",
                color: "gray",
              }}
            >
              DZ
            </Text>
            <Text
              style={{
                fontSize: 14,
                fontWeight: "bold",
                color: "gray",
                marginLeft: 5,
              }}
            >
              {item.price}
            </Text>
          </View>
        </View>

        {Booked ? (
          <Feather name="bookmark" size={20} color="black" />
        ) : (
          <MaterialCommunityIcons name="bookmark" size={20} color="black" />
        )}
      </View>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  conatiner: {
    width: 185,
    height: 220,
    borderRadius: 15,
    backgroundColor: "white",
  },
  imagebox: {
    borderRadius: 15,
    flex: 0.7,
    backgroundColor: "#EEEEF0",
    // backgroundColor: "white",
  },
  contentbox: {
    flex: 0.3,
    flexDirection: "row",
    justifyContent: "space-evenly",
    alignItems: "center",
  },
  image: {
    width: "100%",
    height: "100%",
    resizeMode: "stretch",

    borderRadius: 15,
  },
  tagscontainer: {
    // display: "flex",
    // flexDirection: "row",
    width: "100%",
    height: "40%",
    borderRadius: 10,
  },
  tags: {
    backgroundColor: "lightgray",
    borderRadius: 5,
    paddingHorizontal: 2,
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    marginLeft: 2,
  },
  title: {
    width: "80%",
    height: "60%",
    justifyContent: "space-evenly",
  },
});
