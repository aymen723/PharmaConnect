import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
  Button,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import { router } from "expo-router";
import { useCartStore } from "../zustand/store";

export default function CartPayment({
  delivery,
  pharmacyid,
}: {
  pharmacyid: number;
  delivery: boolean;
}) {
  const { cart } = useCartStore();
  const [OrderTotal, setOrderTotal] = useState<number>(0);
  const [deliverycost, setdeliverycost] = useState<number>(0);
  const [taxcost, settaxcost] = useState<number>(0);
  const [Total, setTotal] = useState<number>(0);

  function Tocheckout() {
    router.push({
      pathname: "/Screens/Checkout",
      params: {
        delivery: delivery,
        idpharamcy: pharmacyid,
      },
    });
  }
  useEffect(() => {
    if (delivery === true) {
      let order = 0;
      cart.forEach((element) => {
        order = order + element.product.price * element.count;
      });
      setdeliverycost(200);
      setOrderTotal(order);
      setTotal(200 + order);
    } else {
      let order = 0;
      cart.forEach((element) => {
        order = order + element.product.price * element.count;
      });
      setdeliverycost(0);

      setOrderTotal(order);
      setTotal(order);
    }
  }, [cart, delivery]);

  return (
    <View style={styles.container}>
      <View style={styles.orderinfo}>
        <Text style={styles.titleinfo}>Payment Summary</Text>
        <ScrollView
          overScrollMode="never"
          showsHorizontalScrollIndicator={false}
          showsVerticalScrollIndicator={false}
        >
          <View style={styles.infotext}>
            <Text style={styles.textstyle}>Order Total</Text>
            <Text>{OrderTotal}</Text>
          </View>
          <View style={styles.infotext}>
            <Text style={styles.textstyle}>Delivery</Text>
            <Text>{deliverycost}</Text>
          </View>

          <View style={[styles.infotext, { height: 60 }]}>
            <Text
              style={[
                styles.textstyle,
                { color: COLORSS.textcolor, fontSize: 16, fontWeight: "600" },
              ]}
            >
              Total
            </Text>
            <Text>{Total}</Text>
          </View>
        </ScrollView>
      </View>
      <View style={styles.orderbutton}>
        <TouchableOpacity
          onPress={() => {
            Tocheckout();
          }}
          style={Gstyles.BigButton}
        >
          <Text style={Gstyles.BigButtonText}>Place Order</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    // borderWidth: 1,
    // borderColor: "green",
    height: "40%",
    justifyContent: "center",
    alignItems: "center",
    width: "100%",
  },
  orderinfo: {
    width: "90%",
    height: "70%",
    alignItems: "flex-start",
    // borderWidth: 1,
    // borderColor: "red",
  },
  infotext: {
    width: "100%",
    height: 45,
    // borderWidth: 1,
    // borderColor: "black",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  orderbutton: {
    width: "100%",
    height: "30%",
    // borderWidth: 1,
    // borderColor: "orange",
    justifyContent: "center",
    alignItems: "center",
  },
  titleinfo: {
    color: COLORSS.textcolor,
    fontWeight: "bold",
    fontSize: 18,
    lineHeight: 20.26,
    marginBottom: 5,
  },
  textstyle: {
    color: "rgba(9, 15, 71, 0.45)",
    fontWeight: "400",
    fontSize: 15,
    lineHeight: 18,
  },
});
