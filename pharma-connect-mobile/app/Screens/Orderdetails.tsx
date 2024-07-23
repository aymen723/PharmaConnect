import { View, Text, ActivityIndicator, Image, ScrollView } from "react-native";
import React, { useEffect, useState } from "react";
import { StyleSheet } from "react-native";
import { COLORSS, Gstyles, SHADOWS } from "../constants/theme";
import { useLocalSearchParams } from "expo-router";
import { fetchOrderById } from "../client/api/stockService/orderApi";
import { OrderRespData } from "../client/types/responses/StockResponses";
import axios from "axios";
import { Buffer } from "buffer";

export default function Orderdetails() {
  const { id } = useLocalSearchParams();
  const [Orderdetail, setOrderdetail] = useState<OrderRespData | undefined>();
  const [qrcode, setqrcode] = useState<string>();

  function fetchorder() {
    if (id) {
      const order: number = id as number;
      fetchOrderById(order)
        .then((res) => {
          console.log("here 1", res);
          setOrderdetail(res.data);
        })
        .catch((err) => {
          console.log("here 2", err);
        });
    }
  }

  function fetchqrcode() {
    axios
      .get(
        "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" +
          Orderdetail?.secret,
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
    fetchorder();
    fetchqrcode();
  }, [Orderdetail?.secret]);

  return (
    <ScrollView style={Gstyles.container}>
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
      <>
        {Orderdetail ? (
          <View style={styles.Pharmacyinfo}>
            <Text style={styles.Pharmacytitle}>
              {Orderdetail.pharmacy.name}
            </Text>
          </View>
        ) : null}
      </>
      <>
        {Orderdetail?.purchases.map((item) => {
          return (
            <View style={styles.productitem} key={item.product.id}>
              <View style={styles.item}>
                <Image
                  resizeMode="contain"
                  style={{ height: 70, width: 70 }}
                  source={{ uri: item.product.picture }}
                ></Image>
                <Text>{item.count}</Text>
                <Text>DA {item.productPrice}</Text>
              </View>
            </View>
          );
        })}
      </>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  qrcodeView: {
    width: "100%",
    height: 200,
    justifyContent: "center",
    alignItems: "center",
  },
  productsView: {},
  productitem: {
    width: "100%",
    height: 90,
    justifyContent: "center",
    alignItems: "center",
  },
  item: {
    width: "90%",
    height: 80,
    backgroundColor: "white",
    borderRadius: 10,
    ...SHADOWS.small,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingLeft: 20,
    paddingRight: 20,
  },
  Pharmacyinfo: {
    width: "100%",
    height: 60,
    justifyContent: "center",
    alignItems: "flex-start",
    paddingLeft: 22,
  },
  Pharmacytitle: {
    fontSize: 22,
    fontWeight: "500",
  },
});
