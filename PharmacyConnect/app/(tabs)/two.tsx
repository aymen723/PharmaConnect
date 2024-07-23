import {
  ActivityIndicator,
  FlatList,
  StyleSheet,
  TouchableOpacity,
} from "react-native";

import EditScreenInfo from "@/components/EditScreenInfo";
import { Text, View } from "@/components/Themed";
import { COLORSS, Gstyles, SHADOWS } from "@/constants/theme";
import { fetchOrdres } from "@/client/api/stockService/orderApi";
import { useEffect, useState } from "react";
import { OrderRespData } from "@/client/types/responses/StockResponses";
import { Page } from "@/client/types/responses";
import { Entypo } from "@expo/vector-icons";
import moment from "moment";
import { router } from "expo-router";

export default function TabTwoScreen() {
  const [Orders, setOrders] = useState<Page<OrderRespData> | undefined>();

  function getOrders() {
    fetchOrdres()
      .then((res) => {
        console.log("here is the the data of achats", res.data.content);
        setOrders(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    getOrders();
  }, []);

  function RenderItem({ item }: { item: OrderRespData }) {
    const date = moment(item.date).format("MMM DD, YYYY");

    return (
      <TouchableOpacity
        activeOpacity={0.7}
        onPress={() => {
          router.push({
            pathname: "/Screens/Orderdetail",
            params: {
              orderId: item.id,
            },
          });
        }}
        style={styles.orderView}
      >
        <View style={styles.orderItem}>
          <View style={styles.orderHeader}>
            <Text style={styles.orderDate}>{date}</Text>
            <Text style={[styles.orderStatus, getStatusStyle(item.status)]}>
              {item.status}
            </Text>
          </View>
          <View style={styles.orderBody}>
            <Text style={styles.pharmacyName}>{item.pharmacy.name}</Text>
            <Text style={styles.orderPrice}>DA {item.price.toFixed(2)}</Text>
          </View>
          <View style={styles.orderFooter}>
            <Text style={styles.orderSecret}>Secret: {item.secret}</Text>
          </View>
        </View>
      </TouchableOpacity>
    );
  }

  function getStatusStyle(status: string) {
    switch (status) {
      case "INITIALIZING":
        return styles.statusInitializing;
      case "PENDING":
        return styles.statusPending;
      case "CANCELED":
        return styles.statusCanceled;
      case "DELIVERING":
        return styles.statusDelivering;
      case "FINALISED":
        return styles.statusFinalised;
      default:
        return styles.statusDefault;
    }
  }
  return (
    <View style={Gstyles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          onPress={() => {
            router.push("/Screens");
          }}
          style={styles.cameraButton}
        >
          <Entypo name="camera" size={24} color={COLORSS.Green} />
        </TouchableOpacity>
      </View>
      {Orders ? (
        <FlatList
          style={{ backgroundColor: COLORSS.maingray }}
          data={Orders?.content}
          renderItem={RenderItem}
        />
      ) : (
        <ActivityIndicator color={COLORSS.Green} />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  header: {
    width: "100%",
    height: 60,
    backgroundColor: COLORSS.maingray,
    justifyContent: "center",
    alignItems: "flex-end",
    paddingRight: 20,
  },
  cameraButton: {
    backgroundColor: "white",
    height: 50,
    width: 50,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 50,
  },
  orderView: {
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
    paddingVertical: 10,
  },
  orderItem: {
    backgroundColor: "white",
    width: "90%",
    borderRadius: 10,
    padding: 15,
    ...SHADOWS.small,
  },
  orderHeader: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  orderDate: {
    fontSize: 16,
    color: "#666",
    fontFamily: "Outfit",
  },
  orderStatus: {
    fontSize: 16,
    fontWeight: "bold",
    fontFamily: "Outfit",
  },
  statusInitializing: {
    color: "#FFA500", // orange
    fontFamily: "Outfit",
  },
  statusPending: {
    color: "#FFD700", // gold
    fontFamily: "Outfit",
  },
  statusCanceled: {
    color: "#FF0000", // red
    fontFamily: "Outfit",
  },
  statusDelivering: {
    color: "#1E90FF", // dodgerblue
    fontFamily: "Outfit",
  },
  statusFinalised: {
    color: "#32CD32", // limegreen
    fontFamily: "Outfit",
  },
  statusDefault: {
    color: "#000", // black
    fontFamily: "Outfit",
  },
  orderBody: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 10,
    fontFamily: "Outfit",
  },
  pharmacyName: {
    fontSize: 18,
    fontWeight: "600",
    fontFamily: "Outfit",
  },
  orderPrice: {
    fontSize: 18,
    fontWeight: "600",
    fontFamily: "Outfit",
  },
  orderFooter: {
    borderTopWidth: 1,
    borderTopColor: "#eee",
    paddingTop: 10,
    fontFamily: "Outfit",
  },
  orderSecret: {
    fontSize: 14,
    color: "#666",
    fontFamily: "Outfit",
  },
});
