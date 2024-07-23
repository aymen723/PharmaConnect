import { View, Text, StyleSheet, Button, TouchableOpacity } from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import Delivery from "../Component/Delivery";
import { CheckBox } from "@rneui/themed";
import { useCartStore } from "../zustand/store";
import { useLocalSearchParams } from "expo-router";
import { postPayment } from "../client/api/paymentService/paymentApi";
import { PaymentCreationRequest } from "../client/types/requests/paymentRequests";
import { PaymentRespData } from "../client/types/responses/paimentResponses";
import * as WebBrowser from "expo-web-browser";
import * as Location from "expo-location";

export default function Checkout() {
  const [result, setResult] = useState(null);
  const { delivery, idpharamcy } = useLocalSearchParams();
  const { cart } = useCartStore();
  const [OrderTotal, setOrderTotal] = useState<number>();
  const [payment, setpayment] = useState<PaymentRespData | undefined>();
  const [loc, setLocation] = useState<Location.LocationObject | undefined>();
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  async function GetLocation() {
    const { status } = await Location.requestForegroundPermissionsAsync();
    if (status !== "granted") {
      setErrorMsg("Permission to access location was denied");
      return;
    }

    const location = await Location.getCurrentPositionAsync({});
    console.log(location);
    console.log(location.coords.altitude);
    console.log(location.coords.longitude);
    setLocation(location);
  }

  function Placeorder() {
    const payment: PaymentCreationRequest = {
      delivery: true,
      x: loc?.coords.longitude,
      y: loc?.coords.latitude,
      pharmacyId: 7,
      products: cart.map((item) => {
        return {
          productId: item.product.id,
          count: item.count,
        };
      }),
    };

    console.log(payment);
    postPayment(payment)
      .then((res) => {
        console.log(res.data);
        setpayment(res.data);
        WebBrowser.openBrowserAsync(res.data.checkouturl);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  useEffect(() => {
    let order = 0;
    cart.forEach((element) => {
      order = order + element.product.price * element.count;
    });
    setOrderTotal(order);

    GetLocation();
  }, [cart]);
  return (
    <View style={Gstyles.container}>
      <View
        style={{
          width: "95%",
          height: 50,
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <View style={styles.orderinfo}>
          <Text style={{ color: "rgba(9, 15, 71, 0.45)" }}>
            {cart.length} Items in your cart
          </Text>
          <View style={styles.ordertotalprice}>
            <Text style={{ color: "rgba(9, 15, 71, 0.45)", fontSize: 14 }}>
              Total
            </Text>
            <Text style={{ fontSize: 16, color: COLORSS.textcolor }}>
              Da {OrderTotal}
            </Text>
          </View>
        </View>
      </View>
      <View style={styles.orderaddress}>
        <Delivery></Delivery>
      </View>
      <View style={styles.checkoutcontainer}>
        <View
          style={{
            width: "100%",
            height: 70,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <TouchableOpacity
            onPress={() => {
              Placeorder();
            }}
            style={Gstyles.BigButton}
          >
            <Text style={Gstyles.BigButtonText}>Place Order</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  orderinfo: {
    // borderWidth: 1,
    // borderColor: "red",
    width: "90%",
    height: "100%",
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
  },
  ordertotalprice: {
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  orderaddress: {
    width: "100%",
    height: "50%",
    // borderWidth: 1,
    // borderColor: "black",
    justifyContent: "center",
    alignItems: "center",
  },
  checkoutcontainer: {
    width: "100%",
    height: "43%",
    // borderWidth: 1,
    // borderColor: "black",
    justifyContent: "flex-end",
    alignItems: "center",
  },
  PaymentTitle: {
    // borderColor: "green",
    // borderWidth: 1,
    height: 60,
    width: "90%",
    justifyContent: "center",
    alignItems: "flex-start",
  },
  methodcheck: {
    width: "90%",
    height: 60,
    flexDirection: "row",
    // borderColor: "black",
    // borderWidth: 1,
    borderRadius: 15,
    justifyContent: "space-evenly",
  },
  methodView: {
    // borderWidth: 1,
    // borderColor: "red",
    width: "100%",
    justifyContent: "space-around",
    alignItems: "center",
    height: 175,
  },
});
