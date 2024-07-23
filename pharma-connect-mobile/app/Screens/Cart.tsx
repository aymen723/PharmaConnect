import {
  View,
  Text,
  FlatList,
  StyleSheet,
  TouchableOpacity,
  Button,
} from "react-native";
import React, { useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import { StatusBar } from "expo-status-bar";
import CartPayment from "../Component/CartPayment";
import { router, useLocalSearchParams } from "expo-router";
import { ProductRespData } from "../client/types/responses/StockResponses";
import { CartItemtype, useCartStore } from "../zustand/store";
import CartItem from "../Component/CartItem";
import BouncyCheckbox from "react-native-bouncy-checkbox";
export default function Cart() {
  const { cart, deleteitem } = useCartStore();
  const [delivery, setDelivery] = useState<boolean>(false);
  const { id } = useLocalSearchParams();
  const renderitem = ({ item }: { item: CartItemtype }) => {
    return <CartItem item={item} key={item.product.id} />;
  };

  return (
    <View style={Gstyles.container}>
      <StatusBar backgroundColor={COLORSS.maingray}></StatusBar>

      {cart.length > 0 ? (
        <>
          <View style={Styles.ContainerList}>
            <View style={Styles.additem}>
              <Text style={{ color: "rgba(9, 15, 71, 0.45)" }}>
                {cart.length} Items in your cart
              </Text>
              <View style={{ width: 100 }}>
                <BouncyCheckbox
                  size={25}
                  fillColor={COLORSS.Green}
                  unFillColor={COLORSS.maingray}
                  text="Delivery"
                  iconStyle={{ borderColor: COLORSS.Green }}
                  innerIconStyle={{ borderWidth: 2 }}
                  textStyle={{
                    color: "rgba(9, 15, 71, 0.45)",
                    fontFamily: "JosefinSans-Regular",
                  }}
                  onPress={(isChecked: boolean) => {
                    setDelivery(isChecked);
                  }}
                />
              </View>
            </View>
            <FlatList
              data={cart}
              showsHorizontalScrollIndicator={false}
              showsVerticalScrollIndicator={false}
              overScrollMode="never"
              renderItem={renderitem}
            />
          </View>
          <CartPayment delivery={delivery} pharmacyid={id}></CartPayment>
        </>
      ) : (
        <View style={Styles.ContainerList}>
          <View style={Styles.additem}>
            <Text style={{ color: "rgba(9, 15, 71, 0.45)" }}>
              Items in your cart
            </Text>
            <TouchableOpacity
              onPress={() => {
                // router.back();
              }}
              style={{ flexDirection: "row" }}
            >
              {/* <Plus color={COLORSS.purpal} size={20} /> */}
              <Text style={{ color: COLORSS.purpal }}>Add More</Text>
            </TouchableOpacity>
          </View>
          <View style={Styles.emptybox}>
            <Text style={{ color: "gray" }}>No Item in the Cart</Text>
          </View>
        </View>
      )}
    </View>
  );
}

const Styles = StyleSheet.create({
  ContainerList: {
    height: "60%",
    justifyContent: "flex-start",
    alignItems: "center",
    width: "100%",
  },
  additem: {
    width: "90%",
    height: 30,

    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  emptybox: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    height: "100%",
  },
});
