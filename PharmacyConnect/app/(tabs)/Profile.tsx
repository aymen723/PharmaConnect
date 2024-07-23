import {
  View,
  Text,
  StyleSheet,
  Image,
  ActivityIndicator,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "@/constants/theme";
import { StatusBar } from "expo-status-bar";
import { useUserStore } from "@/Hooks/zustand/zustand";
import { fetchAccountProfile } from "@/client/api/authService/accountApi";
import { UserProfile } from "@/client/types/responses/authResponses";
import { fetchPharmacyUptime } from "@/client/api/stockService/pharmacyApi";
import { PharmacyUptime } from "@/client/types/responses/StockResponses";
import { AntDesign } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { router } from "expo-router";
export default function Profile() {
  const { user } = useUserStore();
  const [userProfile, setUserProfile] = useState<UserProfile | undefined>();
  const [uptime, setuptime] = useState<PharmacyUptime | undefined>();
  function fetchuser() {
    if (user) {
      fetchAccountProfile(user?.id)
        .then((res) => {
          console.log(res.data);
          setUserProfile(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
      fetchPharmacyUptime(user.id)
        .then((res) => {
          console.log(res.data);
          setuptime(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }
  useEffect(() => {
    fetchuser();
  }, []);

  function logout() {
    AsyncStorage.removeItem("@token");
    AsyncStorage.removeItem("@User");
    AsyncStorage.clear().then(() => {
      router.replace("/(auth)/Signin");
    });
  }

  return (
    <View style={Gstyles.container}>
      <StatusBar style="auto" />
      {userProfile ? (
        <View style={styles.profileContainer}>
          <View style={styles.header}>
            <TouchableOpacity
              onPress={() => {
                logout();
              }}
            >
              <AntDesign name="logout" size={30} color={COLORSS.purpal} />
            </TouchableOpacity>
          </View>
          {userProfile.picture ? (
            <Image
              source={{ uri: userProfile.picture }}
              style={styles.profilePicture}
            />
          ) : (
            <View style={styles.placeholderPicture}>
              <Text style={styles.placeholderText}>
                {userProfile.username.charAt(0)}
              </Text>
            </View>
          )}
          <Text style={styles.username}>{userProfile.username}</Text>
          <Text style={styles.email}>{userProfile.email}</Text>
          <View style={styles.roleView}>
            <Text style={styles.role}>{userProfile.role}</Text>
          </View>
        </View>
      ) : (
        <View>
          <ActivityIndicator size={"large"} color={COLORSS.Green} />
        </View>
      )}

      {uptime ? (
        <View style={styles.uptimeContainer}>
          <Text
            style={[
              styles.uptimeText,
              uptime.open ? styles.open : styles.closed,
            ]}
          >
            {uptime.open ? "Open" : "Closed"}
          </Text>
          {uptime.uptimes && uptime.uptimes.length > 0 && (
            <View style={styles.uptimeList}>
              {uptime.uptimes.map((time, index) => (
                <View key={index} style={styles.uptimeItem}>
                  <Text style={styles.uptimeDay}>{time.day}</Text>
                  <Text style={styles.uptimeHours}>
                    {time.openTime} - {time.closeTime}
                  </Text>
                </View>
              ))}
            </View>
          )}
        </View>
      ) : (
        <Text style={styles.uptimeLoadingText}>Loading uptime info...</Text>
      )}
    </View>
  );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#f0f0f0",
  },
  header: {
    width: "100%",
    height: 60,
    justifyContent: "center",
    alignItems: "flex-end",
    paddingRight: 20,
  },
  profileContainer: {
    alignItems: "center",
    // padding: 20,
    backgroundColor: COLORSS.maingray,
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 5,
  },
  profilePicture: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 20,
  },
  placeholderPicture: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: "#ccc",
    alignItems: "center",
    justifyContent: "center",
    marginBottom: 20,
  },
  placeholderText: {
    fontSize: 40,
    color: "#fff",
  },
  username: {
    fontSize: 30,
    fontWeight: "800",
    marginBottom: 10,
    fontFamily: "Outfit",
  },
  email: {
    fontSize: 18,
    color: "#666",
    fontFamily: "Outfit",
  },
  role: {
    fontSize: 20,
    color: "white",
    fontFamily: "Outfit",
  },
  pharmacyContainer: {
    marginTop: 10,
  },
  pharmacyText: {
    fontSize: 16,
    color: "#333",
  },
  noPharmacyText: {
    fontSize: 16,
    color: "#999",
  },
  roleView: {
    paddingVertical: 10,
    paddingHorizontal: 10,
    borderRadius: 5,
    backgroundColor: COLORSS.Green,
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 10,
    marginTop: 10,
  },

  uptimeContainer: {
    marginTop: 10,
    alignItems: "center",
  },
  uptimeText: {
    fontSize: 18,
    fontWeight: "600",
  },
  open: {
    color: "green",
    fontFamily: "Outfit",
  },
  closed: {
    color: "red",
    fontFamily: "Outfit",
  },
  uptimeList: {
    marginTop: 10,
    width: "100%",
  },
  uptimeItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingVertical: 5,
    paddingHorizontal: 20,
    backgroundColor: "#fff",
    borderRadius: 5,
    marginBottom: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
    fontFamily: "Outfit",
  },
  uptimeDay: {
    fontSize: 16,
    color: "#333",
    fontFamily: "Outfit",
  },
  uptimeHours: {
    fontSize: 16,
    color: "#666",
    fontFamily: "Outfit",
  },
  uptimeLoadingText: {
    fontSize: 16,
    color: "#999",
    marginTop: 10,
    fontFamily: "Outfit",
  },
});
