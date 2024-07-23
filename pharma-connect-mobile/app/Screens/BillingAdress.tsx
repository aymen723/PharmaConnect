import { View, Text, StatusBar, ScrollView, StyleSheet } from "react-native";
import React from "react";
import { COLORSS, Gstyles, SHADOWS } from "../constants/theme";
import { Feather } from "@expo/vector-icons";
import { Entypo } from "@expo/vector-icons";
import { FontAwesome5 } from "@expo/vector-icons";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { TouchableOpacity } from "react-native";
export default function BillingAdress() {
  return (
    <View style={Gstyles.container}>
      <>
        <TouchableOpacity style={style.ButtonADD}>
          <Entypo name="plus" size={24} color={COLORSS.Green} />
        </TouchableOpacity>
        <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>
      </>
      <View style={style.addressview}>
        <View style={style.addresscard}>
          <View style={style.addresstitle}>
            <Text style={style.title}>Address name: Home</Text>
          </View>
          <View
            style={{
              justifyContent: "center",
              height: "80%",
            }}
          >
            <View style={style.addressinfo}>
              <Feather name="phone" size={16} color={COLORSS.Green} />
              <Text style={style.txt}>0559679320</Text>
            </View>
            <View style={[style.addressinfo]}>
              <Entypo name="address" size={16} color={COLORSS.Green} />
              <Text style={style.txt}>Cite 250 BT 5 N 4</Text>
            </View>
            <View style={style.addressinfo}>
              <FontAwesome5 name="city" size={16} color={COLORSS.Green} />
              <Text style={style.txt}>Constantine</Text>
            </View>
            <View style={style.addressinfo}>
              <MaterialCommunityIcons
                name="city-variant"
                size={16}
                color={COLORSS.Green}
              />
              <Text style={style.txt}>Elkhroub</Text>
            </View>
          </View>
        </View>
      </View>
    </View>
  );
}

const style = StyleSheet.create({
  addressview: {
    alignItems: "center",
  },
  addresscard: {
    width: "90%",
    height: 200,
    backgroundColor: "white",
    borderRadius: 20,
    ...SHADOWS.medium,
  },
  addresstitle: {
    height: 40,
    justifyContent: "center",
    alignItems: "center",
  },
  addressinfo: {
    flexDirection: "row",
    height: 30,
    width: "70%",
    // borderWidth: 1,
    // borderColor: "black",
    alignItems: "center",
    // justifyContent: "space-evenly",
    paddingLeft: 20,
  },
  title: {
    color: COLORSS.Green,
    fontSize: 18,
    fontWeight: "800",
    letterSpacing: 1,
  },
  txt: {
    color: "black",
    fontSize: 15,
    fontWeight: "500",
    letterSpacing: 1,
    marginLeft: 20,
  },
  ButtonADD: {
    position: "absolute",
    zIndex: 1,
    backgroundColor: "white",
    width: 55,
    height: 55,
    borderRadius: 60,
    bottom: 50,
    right: 30,
    justifyContent: "center",
    alignItems: "center",
    ...SHADOWS.small,
  },
});
