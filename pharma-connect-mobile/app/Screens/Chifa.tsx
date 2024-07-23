import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
} from "react-native";
import React from "react";
import { COLORSS, Gstyles, SHADOWS } from "../constants/theme";
import { StatusBar } from "expo-status-bar";
import { Entypo } from "@expo/vector-icons";

const cart = {
  ss: "1427-2839-378-2032",
  name: "Aymen",
  prenom: "Benmohammed",
  birthday: "04/03/2001",
};
export default function Chifa() {
  return (
    <View style={Gstyles.container}>
      <>
        <TouchableOpacity style={styles.ButtonADD}>
          <Entypo name="plus" size={24} color={COLORSS.Green} />
        </TouchableOpacity>
        <StatusBar backgroundColor={COLORSS.maingray} />
      </>
      <View style={{ justifyContent: "center", alignItems: "center", flex: 1 }}>
        <View style={styles.card}>
          <View style={styles.cardtitle}>
            <Text style={styles.title}>Cart CHIFA</Text>
          </View>
          <View style={styles.cardSS}>
            <Text style={styles.txt}>{cart.ss}</Text>
          </View>
          <View style={styles.cardinfos}>
            <View style={styles.infolist}>
              <View style={styles.info}>
                <Text style={styles.txt}>Name: </Text>
                <Text style={styles.txt}>{cart.name}</Text>
              </View>
              <View style={styles.info}>
                <Text style={styles.txt}>Prenom: </Text>
                <Text style={styles.txt}>{cart.prenom}</Text>
              </View>
              <View style={styles.info}>
                <Text style={styles.txt}>Birthday : </Text>
                <Text style={styles.txt}>{cart.birthday}</Text>
              </View>
            </View>
          </View>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
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
  card: {
    width: "90%",
    height: 200,
    borderRadius: 20,
    backgroundColor: "green",
    ...SHADOWS.medium,
  },
  cardtitle: {
    justifyContent: "center",
    alignItems: "center",
    height: 40,
  },
  cardSS: {
    // borderWidth: 1,
    // borderColor: "white",
    justifyContent: "center",
    alignItems: "center",
    height: 40,
  },
  title: {
    color: "white",
    fontSize: 18,
    fontWeight: "800",
    letterSpacing: 1,
  },
  txt: {
    color: "white",
    fontSize: 15,
    fontWeight: "500",
    letterSpacing: 1,
  },
  cardinfos: {
    // borderWidth: 1,
    // borderColor: "white",
    height: "55%",
    justifyContent: "center",
  },
  infolist: {
    // borderWidth: 1,
    // borderColor: "white",
    width: "80%",
    marginLeft: 20,
  },
  info: { flexDirection: "row" },
});
