import { View, Text, StyleSheet, TouchableOpacity } from "react-native";
import React from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import AntDesign from "@expo/vector-icons/AntDesign";
import {
  router,
  useLocalSearchParams,
  useNavigation,
  useRouter,
} from "expo-router";
import Feather from "@expo/vector-icons/Feather";

export default function Header() {
  const params = useLocalSearchParams();

  return (
    <View style={[Gstyles.headerContainer, styles.headerpostion]}>
      <TouchableOpacity
        onPress={() => {
          router.back();
        }}
        style={styles.backbutton}
      >
        <AntDesign name="left" size={24} color="black" />
      </TouchableOpacity>
      <View style={styles.Producttitle}>
        <Text
          style={{
            color: COLORSS.Green,
            fontSize: 20,
            fontWeight: "bold",
          }}
        >
          {params.name}
        </Text>
      </View>
      <TouchableOpacity
        onPress={() => {
          router.push("/Screens/Cart");
        }}
        style={styles.backbutton}
      >
        <Feather name="shopping-cart" size={24} color="black" />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  backbutton: {
    width: "15%",
    height: "100%",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    // borderWidth: 1,
    // borderColor: "black",
  },
  Producttitle: {
    // borderWidth: 1,
    // borderColor: "red",
    width: "auto",
    justifyContent: "center",
    alignItems: "center",
    marginLeft: 10,
  },
  headerpostion: {
    zIndex: 1,
  },
});
