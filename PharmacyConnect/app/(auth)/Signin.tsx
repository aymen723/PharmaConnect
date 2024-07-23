import { View, Text, StyleSheet, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import { StatusBar } from "expo-status-bar";
import { COLORSS, Gstyles } from "@/constants/theme";
import { TextInput } from "react-native-element-textinput";
import { router } from "expo-router";
import { credentialAuthentication } from "@/client/api/authService/authentication";
import { LoginResp } from "@/client/types/responses/authResponses";
import { useUserStore } from "@/Hooks/zustand/zustand";
import AsyncStorage from "@react-native-async-storage/async-storage";
export default function Signin() {
  const [username, setusername] = useState("pharmacieillya@gmail.com");
  const [password, setpassword] = useState("123456789");
  const [UserData, setUserData] = useState<LoginResp | undefined>();
  const { user, setUser } = useUserStore();

  function login() {
    // router.replace("/(tabs)/");
    console.log("test");
    credentialAuthentication({ email: username, password: password })
      .then((res) => {
        console.log(res.data);
        setUserData(res.data);
        setUser(res.data);
        AsyncStorage.setItem("@User", JSON.stringify(res.data.profile));
        AsyncStorage.setItem("@token", JSON.stringify(res.data.tokenData)).then(
          () => {
            router.replace("/(tabs)/");
          }
        );
      })
      .catch((err) => {
        console.log(err);
      });
  }
  return (
    <View style={[Gstyles.container, { backgroundColor: "white" }]}>
      <View style={styles.ViewTitle}>
        <Text style={styles.Title}>Pharma Connect</Text>
      </View>
      <View style={styles.ViewInfo}>
        <Text style={styles.Textwelcome}>Welcome Back</Text>
        <Text style={styles.Textinfo}>
          Fill out the information below in order to access your account.
        </Text>
      </View>
      <View style={styles.Viewlogin}>
        <View style={styles.Viewinput}>
          <TextInput
            value={username}
            style={styleInput.input}
            inputStyle={styleInput.inputStyle}
            labelStyle={styleInput.labelStyle}
            placeholderStyle={styleInput.placeholderStyle}
            textErrorStyle={styleInput.textErrorStyle}
            label="Username"
            placeholderTextColor="gray"
            focusColor={COLORSS.Green}
            onChangeText={(text) => {
              setusername(text);
            }}
          />
        </View>
        <View style={styles.Viewinput}>
          <TextInput
            value={password}
            style={styleInput.input}
            inputStyle={styleInput.inputStyle}
            labelStyle={styleInput.labelStyle}
            placeholderStyle={styleInput.placeholderStyle}
            textErrorStyle={styleInput.textErrorStyle}
            label="password"
            placeholderTextColor="gray"
            focusColor={COLORSS.Green}
            onChangeText={(text) => {
              setpassword(text);
            }}
          />
        </View>
      </View>
      <View style={styles.Viewlogbutton}>
        <TouchableOpacity
          onPress={() => {
            login();
          }}
          style={styles.Buttonlogin}
        >
          <Text style={styles.ButtonText}>Log in</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  ViewTitle: {
    width: "100%",
    height: 150,
    // borderWidth: 1,
    // borderColor: "red",
    justifyContent: "center",
    alignItems: "center",
  },
  Title: {
    fontFamily: "Outfit",
    fontSize: 36,
    fontWeight: "600",
  },
  ViewInfo: {
    height: 120,
    width: "100%",
    alignItems: "flex-start",
    justifyContent: "center",
    // borderWidth: 1,
    // borderColor: "red",
    padding: 10,
  },
  Textwelcome: {
    fontFamily: "Outfit",
    fontSize: 24,
    fontWeight: "400",
  },
  Textinfo: {
    fontFamily: "Outfit",
    fontSize: 14,
    fontWeight: "400",
    color: "gray",
  },
  Viewlogin: {
    // borderWidth: 1,
    // borderColor: "red",
    height: 210,
    justifyContent: "space-evenly",
    alignItems: "center",
  },
  Viewinput: {
    width: "85%",
    height: 40,
  },
  Viewlogbutton: {
    height: 100,
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  Buttonlogin: {
    backgroundColor: COLORSS.Green,
    height: 40,
    width: "60%",

    justifyContent: "center",
    alignItems: "center",
    borderRadius: 9,
  },
  ButtonText: {
    fontFamily: "Outfit",
    fontSize: 20,
    fontWeight: "400",
    color: "white",
  },
});

const styleInput = StyleSheet.create({
  input: {
    height: 55,
    paddingHorizontal: 12,
    borderRadius: 8,
    borderWidth: 0.5,
    borderColor: "#DDDDDD",
  },
  inputStyle: { fontSize: 16 },
  labelStyle: {
    fontSize: 14,
    position: "absolute",
    top: -10,
    backgroundColor: "white",
    paddingHorizontal: 4,
    marginLeft: -4,
  },
  placeholderStyle: { fontSize: 16 },
  textErrorStyle: { fontSize: 16 },
});
