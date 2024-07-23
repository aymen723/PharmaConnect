import {
  View,
  Image,
  Text,
  StyleSheet,
  TouchableOpacity,
  ActivityIndicator,
  Button,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import { StatusBar } from "expo-status-bar";
import AntDesign from "@expo/vector-icons/AntDesign";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import Entypo from "@expo/vector-icons/Entypo";
import { router } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { GoogleSignin } from "@react-native-google-signin/google-signin";
import { UserType } from "../client/types/responses/Client";
import Modal from "react-native-modal";
export default function Account() {
  const [User, setUser] = useState<UserType | undefined>();
  const [isModalVisible, setModalVisible] = useState(false);

  const toggleModal = () => {
    setModalVisible(!isModalVisible);
  };
  const getCurrentUser = async () => {
    const currentUser = GoogleSignin.getCurrentUser().then((res) => {
      console.log("here is the user ", res);
      setUser(res?.user);
    });
  };

  const signOut = async () => {
    AsyncStorage.removeItem("@User").then((res) => {
      GoogleSignin.signOut();
      router.replace("/(auth)/Signin");
    });
  };

  useEffect(() => {
    getCurrentUser();
  }, []);

  return (
    <View style={Gstyles.container}>
      <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>
      {User ? (
        <View style={styles.profileitem}>
          <View style={styles.profilesection1}>
            <Image
              source={{
                uri: User.photo,
              }}
              style={styles.profilepic}
            ></Image>
          </View>
          <View style={styles.profilesection2}>
            <Text style={{ fontSize: 23, fontWeight: "bold" }}>
              Hi, {User.name}
            </Text>
            <Text style={{ fontSize: 16, color: "gray" }}>
              welcome to PharmaConnect
            </Text>
          </View>
        </View>
      ) : (
        <ActivityIndicator
          size={"small"}
          color={COLORSS.Green}
        ></ActivityIndicator>
      )}
      <View style={styles.itemcontainer}>
        <TouchableOpacity
          style={styles.items}
          onPress={() => {
            router.push("/Screens/Orders");
          }}
        >
          <View style={styles.itemicon}>
            <MaterialIcons name="payment" size={24} color={COLORSS.Green} />
          </View>
          <View style={styles.itemcontent}>
            <Text style={{ fontWeight: "600" }}>My orders</Text>
            <Entypo name="chevron-right" size={24} color={"black"} />
          </View>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.items}
          onPress={() => {
            router.push("/Screens/BillingAdress");
          }}
        >
          <View style={styles.itemicon}>
            <Entypo name="address" size={24} color={COLORSS.Green} />
          </View>
          <View style={styles.itemcontent}>
            <Text style={{ fontWeight: "600" }}>Billing address</Text>
            <Entypo name="chevron-right" size={24} color={"black"} />
          </View>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.items}
          onPress={() => {
            router.push("/Screens/Chifa");
          }}
        >
          <View style={styles.itemicon}>
            <Entypo name="address" size={24} color={COLORSS.Green} />
          </View>
          <View style={styles.itemcontent}>
            <Text style={{ fontWeight: "600" }}>Chifa</Text>
            <Entypo name="chevron-right" size={24} color={"black"} />
          </View>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.items}
          onPress={() => {
            router.push("/Screens/Settings");
          }}
        >
          <View style={styles.itemicon}>
            <AntDesign name="setting" size={24} color={COLORSS.Green} />
          </View>
          <View style={styles.itemcontent}>
            <Text style={{ fontWeight: "600" }}>Settings</Text>
            <Entypo name="chevron-right" size={24} color={"black"} />
          </View>
        </TouchableOpacity>
        <TouchableOpacity onPress={toggleModal} style={styles.items}>
          <View style={styles.itemicon}>
            <AntDesign name="logout" size={24} color={COLORSS.Green} />
          </View>
          <View style={styles.itemcontent}>
            <Text style={{ fontWeight: "600" }}>Sign Out</Text>
            <Entypo name="chevron-right" size={24} color={"black"} />
          </View>
        </TouchableOpacity>
        <Modal
          style={{ justifyContent: "center", alignItems: "center" }}
          onBackdropPress={() => setModalVisible(false)}
          isVisible={isModalVisible}
        >
          <View style={styles.ModalSignout}>
            <View
              style={{
                height: "60%",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Text style={[Gstyles.BigButtonText, { color: "black" }]}>
                Sign out of PharmaConnect
              </Text>
            </View>
            <View
              style={{ flexDirection: "row", justifyContent: "space-evenly" }}
            >
              <Button
                onPress={() => {
                  setModalVisible(!isModalVisible);
                }}
                title="cancel"
                color={COLORSS.Green}
              ></Button>
              <Button onPress={signOut} title="Sign Out" color={"red"}></Button>
            </View>
          </View>
        </Modal>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  profileitem: {
    width: "100%",
    height: 150,
    flexDirection: "row",
    justifyContent: "center",
  },
  profilepic: {
    width: 100,
    height: 100,
    borderRadius: 50,
    borderWidth: 3,
    resizeMode: "cover",
    borderColor: COLORSS.Green,
  },
  profilesection2: {
    width: "70%",
    justifyContent: "center",
    alignItems: "flex-start",
  },
  profilesection1: {
    justifyContent: "center",
    alignItems: "center",
    width: "30%",
  },
  itemcontainer: {
    // borderWidth: 1,
    // borderColor: "red",
    height: "50%",
    alignItems: "center",
  },
  items: {
    width: "100%",
    height: 60,
    justifyContent: "space-evenly",
    alignItems: "center",

    flexDirection: "row",
  },
  itemicon: {
    width: "20%",
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  itemcontent: {
    width: "70%",
    height: "100%",
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    borderBottomColor: "lightgray",
    borderBottomWidth: 0.75,
  },
  ModalSignout: {
    width: "80%",
    height: 120,
    backgroundColor: "white",
  },
});
