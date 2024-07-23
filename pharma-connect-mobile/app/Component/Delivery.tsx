import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from "react-native";
import React, { useState } from "react";
import { COLORSS } from "../constants/theme";
import AddressItem from "./AddressItem";
import { Address } from "../client/types/requests/paymentRequests";
import BouncyCheckbox from "react-native-bouncy-checkbox";

// import { Plus } from "lucide-react-native";
export default function Delivery() {
  const [empty, setempty] = useState(true);

  const renderItem = ({ item }: { item: Address }) => (
    <View style={styles.addressContainer}>
      <Text style={styles.name}>{item.name}</Text>
      <Text>{item.address}</Text>
      <Text>
        {item.city}, {item.state}
      </Text>
      <Text>Phone: {item.phone}</Text>
      <BouncyCheckbox
        size={25}
        fillColor={COLORSS.Green}
        unFillColor={COLORSS.maingray}
        text="Select"
        iconStyle={{ borderColor: COLORSS.Green }}
        innerIconStyle={{ borderWidth: 2 }}
        textStyle={{
          color: "rgba(9, 15, 71, 0.45)",
          fontFamily: "JosefinSans-Regular",
        }}
        onPress={(isChecked: boolean) => {
          console.log(isChecked);
        }}
      />
    </View>
  );
  return (
    <View style={styles.deliverycontainer}>
      <View style={styles.titlecontainer}>
        <Text
          style={{ fontSize: 18, fontWeight: "bold", color: COLORSS.textcolor }}
        >
          Delivery Address
        </Text>
        <TouchableOpacity
          onPress={() => {
            console.log("new address");
          }}
          style={{ flexDirection: "row" }}
        >
          <Text style={{ color: COLORSS.purpal }}>Add More</Text>
        </TouchableOpacity>
      </View>
      {empty ? (
        <>
          <View style={styles.addresscontainer}>
            <FlatList data={listaddress} renderItem={renderItem} />
          </View>
        </>
      ) : (
        <View>
          <Text>zdz</Text>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  deliverycontainer: {
    width: "90%",
    height: "100%",
  },
  titlecontainer: {
    width: "100%",
    height: "15%",

    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  addresscontainer: {
    width: "100%",
    height: "85%",
  },
  listContainer: {
    padding: 16,
  },
  addressContainer: {
    backgroundColor: "#fff",
    borderRadius: 8,
    padding: 16,
    marginBottom: 16,
    shadowColor: "#000",
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  name: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 8,
  },
});

const listaddress: Address[] = [
  {
    id: 1,
    address: "Haricha ammar cite 506 BT 31 N 403",
    city: "Ain Smara",
    state: "constantine",
    name: "Benmohammed aymen",
    phone: "0559679320",
  },
];
