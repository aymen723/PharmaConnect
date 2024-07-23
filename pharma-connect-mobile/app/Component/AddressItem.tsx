// import React from "react";
// import { View, Text, StyleSheet, FlatList } from "react-native";

// type Address = {
//   id: number;
//   address: string;
//   city: string;
//   state: string;
//   name: string;
//   phone: string;
// };

// type AddressListProps = {
//   addresses: Address[];
// };

// const AddressList: React.FC<AddressListProps> = ({ addresses }) => {
//   const renderItem = ({ item }: { item: Address }) => (
//     <View style={styles.addressContainer}>
//       <Text style={styles.name}>{item.name}</Text>
//       <Text>{item.address}</Text>
//       <Text>
//         {item.city}, {item.state}
//       </Text>
//       <Text>Phone: {item.phone}</Text>
//     </View>
//   );
// };

// const styles = StyleSheet.create({
//   listContainer: {
//     padding: 16,
//   },
//   addressContainer: {
//     backgroundColor: "#fff",
//     borderRadius: 8,
//     padding: 16,
//     marginBottom: 16,
//     shadowColor: "#000",
//     shadowOpacity: 0.1,
//     shadowRadius: 5,
//     elevation: 3,
//   },
//   name: {
//     fontSize: 18,
//     fontWeight: "bold",
//     marginBottom: 8,
//   },
// });
