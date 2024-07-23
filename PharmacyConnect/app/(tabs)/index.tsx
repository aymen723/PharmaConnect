import { ActivityIndicator, FlatList, Image, StyleSheet } from "react-native";

import EditScreenInfo from "@/components/EditScreenInfo";
import { Text, View } from "@/components/Themed";
import { COLORSS, Gstyles, SHADOWS } from "@/constants/theme";
import { StatusBar } from "expo-status-bar";
import { useEffect, useState } from "react";
import { useUserStore } from "@/Hooks/zustand/zustand";
import { fetchStockFromPharmacy } from "@/client/api/stockService/stockApi";
import { AvailableStockRespData } from "@/client/types/responses/StockResponses";
import { Page } from "@/client/types/responses";

export default function TabOneScreen() {
  const { user } = useUserStore();
  const [products, setProducts] = useState<
    Page<AvailableStockRespData> | undefined
  >();

  useEffect(() => {
    if (user) {
      fetchStockFromPharmacy(user?.id)
        .then((res) => {
          setProducts(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  function RenderItem({ item }: { item: AvailableStockRespData }) {
    const { product, price, purchasable } = item;

    return (
      <View style={styles.productItem}>
        {product.picture ? (
          <Image
            source={{ uri: product.picture }}
            style={styles.productImage}
          />
        ) : (
          <View style={styles.productPlaceholder}>
            <Text style={styles.productPlaceholderText}>
              {product.name.charAt(0)}
            </Text>
          </View>
        )}
        <View style={styles.productDetails}>
          <Text style={styles.productName}>{product.name}</Text>
          <Text style={styles.productDescription}>{product.description}</Text>
          <Text style={styles.productPrice}>
            ${price?.toFixed(2) || product.price.toFixed(2)}
          </Text>
          <Text
            style={[
              styles.productStatus,
              { color: purchasable ? "green" : "red" },
            ]}
          >
            {purchasable ? "Available" : "Not Available"}
          </Text>
        </View>
      </View>
    );
  }

  return (
    <View style={Gstyles.container}>
      {products ? (
        <FlatList
          data={products.content}
          renderItem={({ item }) => <RenderItem item={item} />}
          keyExtractor={(item) => item.id.productId.toString()}
          contentContainerStyle={styles.list}
        />
      ) : (
        <View style={styles.loaderContainer}>
          <ActivityIndicator size="large" color={COLORSS.Green} />
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  list: {
    paddingHorizontal: 20,
    paddingTop: 10,
  },
  productItem: {
    flexDirection: "row",
    backgroundColor: "white",
    padding: 15,
    borderRadius: 10,
    marginBottom: 15,
    ...SHADOWS.small,
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
  },
  productDetails: {
    flex: 1,
  },
  productName: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 5,
  },
  productDescription: {
    fontSize: 14,
    color: "#666",
    marginBottom: 5,
  },
  productPrice: {
    fontSize: 16,
    fontWeight: "bold",
    color: COLORSS.Green,
    marginBottom: 5,
  },
  productStatus: {
    fontSize: 14,
  },
});
