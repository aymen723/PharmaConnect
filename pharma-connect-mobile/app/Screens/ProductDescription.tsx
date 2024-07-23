import {
  View,
  Text,
  ScrollView,
  StyleSheet,
  Image,
  ActivityIndicator,
  TouchableOpacity,
  Button,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import { router, useLocalSearchParams } from "expo-router";
import { StatusBar } from "expo-status-bar";
import { fetchProductById } from "../client/api/stockService/productApi";
import { ProductRespData } from "../client/types/responses/StockResponses";
import Tag from "../Component/Tag";
import Ionicons from "@expo/vector-icons/Ionicons";
import Modal from "react-native-modal";
import { useCartStore } from "../zustand/store";
import { fetchPharmaciesByFilter } from "../client/api/stockService/pharmacyApi";
import Header from "../Component/Header";

export default function ProductDescription() {
  const { id, name } = useLocalSearchParams();
  const [Product, setProduct] = useState<ProductRespData>();
  const [isModalVisible, setModalVisible] = useState(false);
  const { cart, append } = useCartStore();

  const toggleModal = () => {
    setModalVisible(!isModalVisible);
  };
  useEffect(() => {
    const idproduct: number = id as number;
    fetchProductById(idproduct)
      .then((res) => {
        console.log("res is here ", res.data);
        setProduct(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [id]);

  function locate() {
    router.push({
      pathname: "/Screens/MapSearch",
      params: {
        ProductID: Product?.id,
      },
    });
  }

  return (
    <ScrollView
      style={Gstyles.container}
      showsHorizontalScrollIndicator={false}
      showsVerticalScrollIndicator={false}
    >
      <StatusBar backgroundColor={COLORSS.white}></StatusBar>
      {Product ? (
        <View style={{ alignItems: "center" }}>
          <TouchableOpacity
            activeOpacity={0.9}
            onPress={toggleModal}
            style={styles.ProductPictureView}
          >
            <Image
              onLoad={() => {
                return (
                  <ActivityIndicator
                    size={"small"}
                    color={"black"}
                  ></ActivityIndicator>
                );
              }}
              style={styles.ProductPicture}
              source={{ uri: Product?.picture }}
            ></Image>
          </TouchableOpacity>
          <View></View>
          <View style={styles.ProductPriceView}>
            <View
              style={{
                flexDirection: "row",
                justifyContent: "center",
                alignItems: "center",

                height: "100%",
                width: "40%",
              }}
            >
              <Text style={{ fontSize: 18, fontWeight: "bold" }}>
                {Product.price}{" "}
              </Text>
              <Text style={{ fontWeight: "bold", color: "gray" }}>Dinar</Text>
            </View>
            <TouchableOpacity
              style={{
                flexDirection: "row",
                justifyContent: "center",
                alignItems: "center",

                height: "100%",
                width: "40%",
              }}
              onPress={() => {
                append({
                  product: Product,
                  count: 1,
                });
                console.log(cart.length);
              }}
            >
              <Ionicons
                name="add-circle-outline"
                size={24}
                color={COLORSS.Green}
              />
              <Text style={{ fontWeight: "bold", color: "gray" }}>
                Add to cart
              </Text>
            </TouchableOpacity>
          </View>
          <View style={styles.ProductTagsView}>
            <Text style={{ padding: 10, fontSize: 18, fontWeight: "bold" }}>
              Product Tags
            </Text>
            <ScrollView
              horizontal
              contentContainerStyle={{
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              {Product.tags.map((item) => {
                return <Tag key={item.id} item={item}></Tag>;
              })}
            </ScrollView>
          </View>
          <View style={styles.ProductDescriptionView}>
            <Text style={{ padding: 10, fontSize: 18, fontWeight: "bold" }}>
              Product Description
            </Text>
            <Text style={{ padding: 10, fontSize: 16, color: "gray" }}>
              {Product.description}
            </Text>
          </View>
          <View style={styles.ProductButton}>
            <TouchableOpacity
              style={styles.Button}
              onPress={() => {
                locate();
              }}
            >
              <Text
                style={{ color: "white", fontSize: 16, fontWeight: "bold" }}
              >
                LOCATE
              </Text>
            </TouchableOpacity>
          </View>
          <Modal
            onBackdropPress={() => setModalVisible(false)}
            isVisible={isModalVisible}
          >
            <View
              style={{
                flex: 1,
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Image
                onLoad={() => {
                  return (
                    <ActivityIndicator
                      size={"small"}
                      color={"black"}
                    ></ActivityIndicator>
                  );
                }}
                style={styles.ModalPicture}
                source={{ uri: Product?.picture }}
              ></Image>
            </View>
          </Modal>
        </View>
      ) : (
        <View style={{ flex: 1 }}>
          <ActivityIndicator
            size={"large"}
            color={COLORSS.Green}
          ></ActivityIndicator>
        </View>
      )}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  ProductPictureView: {
    width: "100%",
    height: 300,
    backgroundColor: "white",
    borderBottomEndRadius: 30,
    borderBottomLeftRadius: 30,
  },
  ProductPicture: {
    width: "100%",
    height: "100%",
    resizeMode: "stretch",
    borderBottomRightRadius: 30,
    borderBottomLeftRadius: 30,
  },
  ProductPriceView: {
    height: 70,
    justifyContent: "space-between",
    alignItems: "center",
    width: "90%",
    flexDirection: "row",
    borderBottomWidth: 0.75,
    borderBottomColor: "gray",
  },
  ProductDescriptionView: {
    width: "90%",
  },
  ProductTagsView: {
    width: "90%",
    height: 100,
  },
  ModalPicture: {
    width: "100%",
    height: 400,
  },
  ProductButton: {
    height: 80,
    justifyContent: "space-around",
    alignItems: "center",
    width: "100%",
    flexDirection: "row",
  },
  Button: {
    height: 40,
    width: "80%",
    borderRadius: 30,
    backgroundColor: COLORSS.Green,
    justifyContent: "center",
    alignItems: "center",
  },
});
