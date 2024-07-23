import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
} from "react-native";
import React, { useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import Ionicons from "@expo/vector-icons/Ionicons";
import { StatusBar } from "expo-status-bar";

export default function MapHeader({ onModalToggle, statechnage }) {
  const [Search, SetSearch] = useState<string | undefined>();

  const handleChange = (e) => {
    SetSearch(e);
    statechnage(e);
  };
  return (
    <View style={Gstyles.Mapheader}>
      <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>

      <TextInput
        value={Search}
        placeholder={"Search"}
        onChangeText={(e) => {
          handleChange(e);
        }}
        style={[Gstyles.searchinput, { backgroundColor: "white" }]}
      ></TextInput>
      <TouchableOpacity style={styles.IconBox} onPress={onModalToggle}>
        <Ionicons name="menu-outline" size={30} color="black" />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  IconBox: {
    borderRadius: 50,
    backgroundColor: "white",
    justifyContent: "center",
    alignItems: "center",
    width: 50,
    height: 50,
  },
});
