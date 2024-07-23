import React from "react";
import { View, Text, StyleSheet, Image, FlatList } from "react-native";
import { AvailableStockRespData } from "../client/types/responses/StockResponses";
import { BottomSheetFlatList } from "@gorhom/bottom-sheet";
import { COLORSS } from "../constants/theme";

export type WeekDay =
  | "Monday"
  | "Tuesday"
  | "Wednesday"
  | "Thursday"
  | "Friday"
  | "Saturday"
  | "Sunday";

export type UptimeRespData = {
  id: number;
  openTime: string;
  closeTime: string;
  day: WeekDay;
};

export type PharmacyUptime = {
  uptimes?: UptimeRespData[];
  open: boolean;
};

export type Location = {
  address: string;
  city: string;
  state: string;
  zipCode: string;
};

export type PharmacyRespData = {
  id: number;
  name: string;
  accountId: number;
  location: Location;
  supportPayment: boolean;
  enabled: boolean;
  picture: string | null;
  phoneNumber: string | null;
  upTimes: PharmacyUptime;
};

export type StockId = {
  productId: number;
  pharmacyId: number;
};

export type PrivateStockData = {
  available: boolean;
  overridden: boolean;
  overriddenAvailability: boolean;
};

export type AvailableStockRespData = {
  id: StockId;
  product: ProductRespData;
  pharmacy: PharmacyRespData;
  price?: number | null;
  purchasable: boolean;
  privateData: PrivateStockData | null;
};

export type ProductRespData = {
  id: number;
  name: string;
  description: string;
  picture: string | null;
};

type PharmacyProductsListProps = {
  stock: AvailableStockRespData[];
};

const PharmacyProductsList: React.FC<PharmacyProductsListProps> = ({
  stock,
}) => {
  const renderItem = ({ item }: { item: AvailableStockRespData }) => (
    <View style={styles.productItem}>
      <Image
        source={{
          uri: item.product.picture || "https://via.placeholder.com/150",
        }}
        style={styles.productImage}
      />
      <View style={styles.productDetails}>
        <Text style={styles.productName}>{item.product.name}</Text>
        <Text style={styles.productDescription}>
          {item.product.description}
        </Text>
        <View style={styles.priceAvailabilityContainer}>
          <Text style={styles.productPrice}>
            {item.price ? `DA ${item.price.toFixed(2)}` : "Price not available"}
          </Text>
          <Text
            style={[
              styles.productAvailability,
              { color: item.privateData?.available ? COLORSS.Green : "red" },
            ]}
          >
            {item.privateData?.available ? "Available" : "Not Available"}
          </Text>
        </View>
      </View>
    </View>
  );

  return (
    <BottomSheetFlatList
      style={{ backgroundColor: COLORSS.white }}
      contentContainerStyle={{ width: "100%" }}
      data={stock}
      renderItem={renderItem}
      keyExtractor={(item) => `${item.id.productId}`}
      contentContainerStyle={styles.listContainer}
      //   onEndReached={handleLoadMore}
      //   onEndReachedThreshold={0.5}
      //   ListFooterComponent={
      //     loadingMore && <ActivityIndicator size="large" color="green" />}
    />
  );
};

const styles = StyleSheet.create({
  listContainer: {
    paddingHorizontal: 16,
    paddingBottom: 24,
  },
  productItem: {
    flexDirection: "row",
    backgroundColor: "#fff",
    borderRadius: 8,
    marginBottom: 16,
    shadowColor: "#000",
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  productImage: {
    width: 120,
    height: 120,
    borderTopLeftRadius: 8,
    borderBottomLeftRadius: 8,
  },
  productDetails: {
    flex: 1,
    padding: 12,
  },
  productName: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 8,
  },
  productDescription: {
    fontSize: 14,
    color: "#555",
    marginBottom: 8,
  },
  priceAvailabilityContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  productPrice: {
    fontSize: 16,
    fontWeight: "600",
  },
  productAvailability: {
    fontSize: 14,
  },
});

export default PharmacyProductsList;
