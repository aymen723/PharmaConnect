import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles, SHADOWS } from "../constants/theme";
import { StatusBar } from "expo-status-bar";
import { getLocalAccessToken } from "@/Hooks/token";
import { fetchBookmarks } from "../client/api/stockService/bookmarkApi";
import { fetchOrdres } from "../client/api/stockService/orderApi";
import { fetchAccountProfile } from "../client/api/authService/accountApi";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { OrderRespData } from "../client/types/responses/StockResponses";
import { Page } from "../client/types/responses";
import moment from "moment";
import { router } from "expo-router";
export default function Orders() {
  const [Orders, setOrders] = useState<Page<OrderRespData> | undefined>();
  const [User, setUser] = useState();
  async function name() {
    console.log("aqwdi hana", await getLocalAccessToken());
  }

  async function getorder() {
    fetchOrdres()
      .then((res) => {
        console.log(res.data);
        setOrders(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    getorder();
  }, []);

  function orderdetail({ item }: { item: OrderRespData }) {}

  const RenderItem = ({ item }: { item: OrderRespData }) => {
    const date = moment(item.date).format("MMM DD, YYYY");
    return (
      <TouchableOpacity
        onPress={() => {
          router.push({
            pathname: "/Screens/Orderdetails",
            params: {
              id: item.id,
            },
          });
        }}
        activeOpacity={0.9}
        style={styles.ViewOrder}
      >
        <View style={styles.Orderitem}>
          <View style={styles.orderpharmacy}>
            <Text style={styles.pharamcytitle}>{item.pharmacy.name}</Text>
            <Text style={styles.pharamcytitle}>DA {item.checkoutPrice}</Text>
          </View>
          <View style={styles.orderdetail}>
            <Text style={styles.datetext}>{date}</Text>
            <View style={styles.statusorder}>
              <Text style={styles.statustext}>{item.status}</Text>
            </View>
          </View>
        </View>
      </TouchableOpacity>
    );
  };
  return (
    <View style={Gstyles.container}>
      <>
        <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>
      </>
      <View>
        <FlatList
          style={{ width: "100%" }}
          data={Orders?.content}
          keyExtractor={(item) => item.id.toString()}
          renderItem={RenderItem}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  ViewOrder: {
    width: "100%",
    height: 150,
    justifyContent: "center",
    alignItems: "center",
  },
  Orderitem: {
    width: "90%",
    height: 120,
    backgroundColor: "white",
    borderRadius: 10,
    ...SHADOWS.small,
    borderWidth: 0.75,
    borderColor: "lightgray",
  },
  orderpharmacy: {
    height: "50%",
    justifyContent: "space-between",
    alignItems: "center",

    flexDirection: "row",
    paddingLeft: 10,
    paddingRight: 10,
  },
  pharamcytitle: {
    fontSize: 20,
    fontWeight: "600",
  },
  datetext: {
    fontSize: 18,
    fontWeight: "400",
    color: "gray",
  },
  orderdetail: {
    height: "50%",
    justifyContent: "space-between",
    alignItems: "center",

    flexDirection: "row",
    paddingLeft: 10,
    paddingRight: 10,
  },
  statustext: {
    color: "white",
    fontWeight: "400",
    opacity: 1,
  },
  statusorder: {
    paddingVertical: 10,
    paddingHorizontal: 10,
    backgroundColor: "#ee8b60",
    // opacity: 0.6,
    borderRadius: 10,
    borderColor: "#ee8b60",
  },
});
