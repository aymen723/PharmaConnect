import {
  View,
  Button,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
} from "react-native";
import * as browser from "expo-web-browser";
import * as Google from "expo-auth-session/providers/google";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles, SHADOWS } from "../constants/theme";
import icon from "../../assets/Images/pharma.png";
import GoogleI from "../../assets/Images/GoogleI.png";
import { router } from "expo-router";
import axios from "axios";
import { StatusBar } from "expo-status-bar";
import {
  GoogleSignin,
  GoogleSigninButton,
  statusCodes,
} from "@react-native-google-signin/google-signin";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useTokenStore, useUserStore } from "../zustand/store";
import {
  googleAccessAuthentication,
  googleIdAuthentication,
} from "../client/api/authService/authentication";
import { LoginResp } from "../client/types/responses/authResponses";
browser.maybeCompleteAuthSession();

export default function Signin() {
  // const { user, setUser } = useUserStore();
  const [authresponse, setauthresponse] = useState<LoginResp | undefined>();
  const { user, setUser } = useUserStore();
  const { token, setToken } = useTokenStore();
  const [request, response, promptasync] = Google.useAuthRequest({
    clientId: process.env.OUSSAMA_Client_ID,
    androidClientId: process.env.CLIENT_ID_ANDROID,
  });
  const [isError, isErrorWithCode] = useState();

  const _signIn = async () => {
    try {
      GoogleSignin.configure({
        iosClientId: process.env.CLIENT_ID_IOS,
        offlineAccess: true,
        webClientId: process.env.CLIENT_ID_WEB,
        androidClientId: process.env.CLIENT_ID_ANDROID,
        scopes: ["profile", "email"],
      });
      await GoogleSignin.hasPlayServices();

      GoogleSignin.signIn().then((res) => {
        if (res.idToken) {
          setUser(res.user);
          googleIdAuthentication(res.idToken as string)
            .then((res) => {
              console.log("here the googleIdAuthentication", res.data);
              setauthresponse(res.data);
              setToken(res.data.tokenData);
              AsyncStorage.setItem("@User", JSON.stringify(res.data));
              AsyncStorage.setItem(
                "@token",
                JSON.stringify(res.data.tokenData)
              );
            })
            .catch((err) => {
              console.log(
                "this error in the Singin googleIdAuthentication",
                err
              );
            });
        }

        router.replace("/tabs/Home");
      });
    } catch (error) {
      if (isErrorWithCode(error)) {
        switch (error.code) {
          case statusCodes.SIGN_IN_CANCELLED:
            // user cancelled the login flow
            break;
          case statusCodes.IN_PROGRESS:
            // operation (eg. sign in) already in progress
            break;
          case statusCodes.PLAY_SERVICES_NOT_AVAILABLE:
            // play services not available or outdated
            break;
          default:
          // some other error happened
        }
      } else {
        // an error that's not related to google sign in occurred
      }
    }
  };

  const getCurrentUser = async () => {
    const currentUser = GoogleSignin.getCurrentUser();
    console.log(currentUser);
  };

  return (
    <View style={[Gstyles.container, { backgroundColor: "white" }]}>
      <StatusBar backgroundColor={COLORSS.Green}></StatusBar>
      <View style={Styles.SigninContainer}>
        <Image style={Styles.Pic} source={icon}></Image>
        {/* <Text style={Styles.Title}>Pharma Connect</Text> */}
      </View>
      <View style={Styles.FooterContainer}>
        <View style={Styles.FooterContent}>
          <View style={Styles.SinginButtons}>
            <View
              style={{
                width: "100%",
                height: 90,
                justifyContent: "center",
                alignItems: "center",
                flexDirection: "row",
                // borderWidth: 1,
                // borderColor: "black",
              }}
            >
              <Text style={{ fontWeight: "bold", fontSize: 20 }}>
                Welcome to{" "}
              </Text>
              <Text
                style={{
                  fontWeight: "bold",
                  fontSize: 20,
                  color: COLORSS.Green,
                }}
              >
                PharmaConnect
              </Text>
            </View>
            <View style={{ height: 30 }}>
              <Text style={{ color: "gray" }}>Sing In with with google</Text>
            </View>
            <TouchableOpacity
              style={Styles.Button}
              onPress={() => {
                // promptasync({ showInRecents: true });
                _signIn();
              }}
            >
              <Image
                style={{ width: 60, height: 60, resizeMode: "center" }}
                source={GoogleI}
              ></Image>
            </TouchableOpacity>
            <View
              style={{
                height: 35,
                justifyContent: "center",
              }}
            >
              <Text style={{ fontSize: 10 }}>
                By registration you agree to Terms of use and Privacy Police
              </Text>
            </View>
          </View>
        </View>
      </View>
    </View>
  );
}

const Styles = StyleSheet.create({
  SigninContainer: {
    flex: 0.7,
    backgroundColor: COLORSS.Green,
    justifyContent: "center",
    alignItems: "center",
  },
  FooterContainer: {
    flex: 0.3,
    backgroundColor: COLORSS.Green,
    // borderWidth: 1,
    // borderColor: "red",
  },
  FooterContent: {
    borderTopLeftRadius: 60,
    borderTopRightRadius: 60,
    flex: 1,
    backgroundColor: "white",
  },
  Pic: {
    width: 300,
    height: 300,
  },
  Title: {
    fontSize: 30,
    color: "white",
    fontStyle: "italic",
    fontWeight: "bold",
    letterSpacing: 2,
    paddingVertical: 20,
  },
  SinginButtons: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    // borderWidth: 1,
    // borderColor: "red",
  },
  Button: {
    borderWidth: 1,
    borderColor: "lightgray",
    width: 70,
    height: 70,
    borderRadius: 15,
    justifyContent: "center",
    alignItems: "center",
  },
});
