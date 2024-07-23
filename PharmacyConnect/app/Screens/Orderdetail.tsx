import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  ScrollView,
  ActivityIndicator,
  TouchableOpacity,
} from "react-native";
import { RouteProp, useRoute } from "@react-navigation/native";
import { OrderRespData } from "@/client/types/responses/StockResponses";
import { COLORSS, Gstyles, SHADOWS } from "@/constants/theme";
import moment from "moment";
import { fetchOrderById } from "@/client/api/stockService/orderApi";
import { StatusBar } from "expo-status-bar";
import { Entypo } from "@expo/vector-icons";
import { router } from "expo-router";
import axios from "axios";
import { Buffer } from "buffer";
type OrderDetailScreenRouteProp = RouteProp<
  { params: { orderId: number } },
  "params"
>;

export default function OrderDetail() {
  const route = useRoute<OrderDetailScreenRouteProp>();
  const { orderId } = route.params;
  const [order, setOrder] = useState<OrderRespData | undefined>();
  const [qrcode, setqrcode] = useState<string>();

  function fetchqrcode() {
    axios
      .get(
        "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" +
          order?.secret,
        {
          responseType: "arraybuffer",
        }
      )
      .then((res) => {
        console.log("here 3", res.data);
        const base64 = Buffer.from(res.data, "binary").toString("base64");
        const imageURI = `data:image/png;base64,${base64}`;
        setqrcode(imageURI);
        console.log("here 4", imageURI);
      });
  }

  useEffect(() => {
    fetchOrderById(orderId)
      .then((res) => {
        setOrder(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
    fetchqrcode();
  }, [orderId]);

  if (!order) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color={COLORSS.Green} />
      </View>
    );
  }

  const orderDate = moment(order.date).format("MMM DD, YYYY");
  const { pharmacy, purchases } = order;

  return (
    <ScrollView style={styles.container}>
      <StatusBar />
      <View style={styles.header}>
        <TouchableOpacity
          onPress={() => {
            router.back();
          }}
          style={styles.backButton}
        >
          <Entypo name="chevron-left" size={30} color={COLORSS.Green} />
        </TouchableOpacity>
      </View>
      <View style={styles.section}>
        <View style={styles.qrcodeView}>
          {qrcode ? (
            <Image
              resizeMode="cover"
              style={{ height: 150, width: 150 }}
              source={{ uri: qrcode }}
            ></Image>
          ) : (
            <ActivityIndicator color={COLORSS.Green} size={"large"} />
          )}
        </View>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Order Details</Text>
        <Text style={styles.text}>Order ID: {order.id}</Text>
        <Text style={styles.text}>Date: {orderDate}</Text>
        <Text style={styles.text}>Status: {order.status}</Text>
        <Text style={styles.text}>Total Price: ${order.price.toFixed(2)}</Text>
        {order.deliveryPrice !== null && (
          <Text style={styles.text}>
            Delivery Price: DA {order.deliveryPrice?.toFixed(2)}
          </Text>
        )}
        <Text style={styles.text}>Secret: {order.secret}</Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Pharmacy Details</Text>
        {pharmacy.picture ? (
          <Image
            source={{ uri: pharmacy.picture }}
            style={styles.pharmacyImage}
          />
        ) : (
          <View style={styles.placeholderPicture}>
            <Text style={styles.placeholderText}>
              {pharmacy.name.charAt(0)}
            </Text>
          </View>
        )}
        <Text style={styles.text}>Name: {pharmacy.name}</Text>
        <Text style={styles.text}>Phone: {pharmacy.phoneNumber || "N/A"}</Text>
        <Text style={styles.text}>
          Support Payment: {pharmacy.supportPayment ? "Yes" : "No"}
        </Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Purchases</Text>
        {purchases.map((purchase, index) => (
          <View key={index} style={styles.purchaseItem}>
            {purchase.product.picture ? (
              <Image
                source={{ uri: purchase.product.picture }}
                style={styles.productImage}
              />
            ) : (
              <View style={styles.productPlaceholder}>
                <Text style={styles.productPlaceholderText}>
                  {purchase.product.name.charAt(0)}
                </Text>
              </View>
            )}
            <View style={styles.productDetails}>
              <Text style={styles.text}>{purchase.product.name}</Text>
              <Text style={styles.text}>
                Price: DA {purchase.productPrice.toFixed(2)}
              </Text>
              <Text style={styles.text}>Quantity: {purchase.count}</Text>
            </View>
          </View>
        ))}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORSS.maingray,
    // paddingHorizontal: 20,
    // paddingVertical: 10,
    paddingTop: 30,
  },
  header: {
    width: "100%",
    height: 60,
    justifyContent: "center",
    paddingLeft: 15,
    marginBottom: 20,
    fontFamily: "Outfit",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  section: {
    backgroundColor: "white",
    padding: 15,
    borderRadius: 10,
    marginBottom: 10,

    ...SHADOWS.small,
    fontFamily: "Outfit",
  },
  qrcodeView: {
    width: "100%",
    height: 200,
    justifyContent: "center",
    alignItems: "center",
  },
  sectionTitle: {
    fontFamily: "Outfit",
    fontSize: 18,
    fontWeight: "800",
    marginBottom: 10,
  },
  text: {
    fontSize: 16,
    marginBottom: 5,
    fontFamily: "Outfit",
  },
  pharmacyImage: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 10,
  },
  placeholderPicture: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: "#ccc",
    alignItems: "center",
    justifyContent: "center",
    marginBottom: 10,
  },
  placeholderText: {
    fontSize: 40,
    color: "#fff",
    fontFamily: "Outfit",
  },
  purchaseItem: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 15,
  },
  productImage: {
    width: 60,
    height: 60,
    borderRadius: 10,
    marginRight: 15,
  },
  productPlaceholder: {
    width: 60,
    height: 60,
    borderRadius: 10,
    backgroundColor: "#ccc",
    alignItems: "center",
    justifyContent: "center",
    marginRight: 15,
  },
  productPlaceholderText: {
    fontSize: 30,
    color: "#fff",
    fontFamily: "Outfit",
  },
  productDetails: {
    flex: 1,
  },
  backButton: {
    height: 50,
    width: 50,
    backgroundColor: "white",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 50,
    ...SHADOWS.small,
  },
});
