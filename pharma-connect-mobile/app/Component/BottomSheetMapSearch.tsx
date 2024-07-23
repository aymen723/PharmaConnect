import {
  View,
  Text,
  StyleSheet,
  Image,
  ActivityIndicator,
  VirtualizedList,
  FlatList,
  TouchableOpacity,
} from "react-native";
import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import BottomSheet, {
  BottomSheetFlatList,
  BottomSheetScrollView,
  BottomSheetView,
} from "@gorhom/bottom-sheet";
import {
  AvailableStockRespData,
  PharmacyRespData,
  PharmacyUptime,
} from "../client/types/responses/StockResponses";
import { fetchPharmacyById } from "../client/api/stockService/pharmacyApi";
import { COLORSS, SHADOWS } from "../constants/theme";
import { fetchStockFromPharmacy } from "../client/api/stockService/stockApi";
import { Page } from "../client/types/responses";
import { fetchPharmacyUptime } from "../client/api/stockService/productApi";
import Productcard from "./Productcard";
import FontAwesome6 from "@expo/vector-icons/FontAwesome6";
import { useCartStore } from "../zustand/store";
import { MaterialIcons } from "@expo/vector-icons";
import Uptime from "./Uptime";
import { Feather } from "@expo/vector-icons";
import { router } from "expo-router";
import PharmacyInfo from "./PharmacyInfo";
import PharmacyProductsList from "./PharmacyProductsList";
export default function BottomSheetMapSearch({
  pharmacy,
}: {
  pharmacy: PharmacyRespData | undefined;
}) {
  const [pharma, setPharmacy] = useState<PharmacyRespData | undefined>();
  const [Stock, setStock] = useState<AvailableStockRespData[]>([]);

  const bottomSheetRef = useRef<BottomSheet>(null);
  const [infoscreen, setinfoscreen] = useState(true);
  const [stockscreen, setstockscreen] = useState(false);
  const [cartscreen, setcartscreen] = useState(false);
  const [pharmacyuptime, setpharmacyuptime] = useState<
    PharmacyUptime | undefined
  >();
  const { cart, deleteitem } = useCartStore();
  const [loading, setLoading] = useState(false);
  const [loadingMore, setLoadingMore] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [page, setPage] = useState(0);

  const snappoints = useMemo(() => ["5%", "50%", "90%"], []);
  const handleSheetChanges = useCallback((index: number) => {
    console.log("handleSheetChanges", index);
  }, []);
  const handleClosePress = useCallback(() => {
    bottomSheetRef.current?.close();
  }, []);

  function fetchpharmacy() {
    if (pharmacy) {
      fetchPharmacyById(pharmacy.id).then((res) => {
        console.log(res.data);
        setPharmacy(res.data);
      });
      fetchPharmacyUptime(pharmacy.id).then((res) => {
        console.log("time for pharmacy", res.data);
        setpharmacyuptime(res.data);
      });
    }
  }

  function fetchstock() {
    if (pharmacy) {
      fetchStockFromPharmacy(pharmacy?.id, { page: 0 })
        .then((res) => {
          console.log(res.data.content);
          setStock(res.data.content);
          setHasMore(res.data.content.length > 0);
        })
        .then((err) => {
          console.log(err);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }
  function hundleStockscreen() {
    setstockscreen(true);
    setcartscreen(false);
    setinfoscreen(false);
  }
  function hundleCartscreen() {
    setstockscreen(false);
    setcartscreen(true);
    setinfoscreen(false);
  }
  function hundleInfoscreen() {
    setstockscreen(false);
    setcartscreen(false);
    setinfoscreen(true);
  }

  // const stock = useCallback(async () => {
  //   if (loadingMore || !hasMore) return;

  //   setLoadingMore(true);
  //   try {
  //     const res = await fetchStockFromPharmacy(pharmacy?.id, { page: 0 });
  //     setStock((prevProducts) => [...prevProducts, ...res.data.content]);
  //     setHasMore(res.data.content.length > 0);
  //   } catch (err) {
  //     console.error("Error fetching products", err);
  //   } finally {
  //     setLoadingMore(false);
  //   }
  // }, [page, loadingMore, hasMore]);
  useEffect(() => {
    fetchpharmacy();
    fetchstock();
    // handleClosePress()
  }, [pharmacy]);

  const getItem = (data: AvailableStockRespData[], index: number) =>
    data[index];

  const getItemCount = (data: AvailableStockRespData[]) => data.length;

  const handleLoadMore = () => {
    if (!loadingMore && hasMore) {
      setPage((prevPage) => prevPage + 1);
      // stock();
    }
  };
  return (
    <BottomSheet
      snapPoints={snappoints}
      ref={bottomSheetRef}
      onChange={handleSheetChanges}
    >
      <View style={styles.sheetButton}>
        <TouchableOpacity
          onPress={() => {
            hundleInfoscreen();
          }}
          style={styles.Buttonscreen}
        >
          <Text style={[styles.Textscreen, { color: COLORSS.Green }]}>
            pharmacy Info
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => {
            hundleStockscreen();
          }}
          style={styles.Buttonscreen}
        >
          <Text style={[styles.Textscreen, { color: "black" }]}>Stock</Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => {
            hundleCartscreen();
          }}
          style={styles.Buttonscreen}
        >
          <Text style={[styles.Textscreen, { color: COLORSS.purpal }]}>
            Cart
          </Text>
        </TouchableOpacity>
      </View>

      {infoscreen ? (
        <>
          <BottomSheetScrollView>
            <View>
              {pharma && pharmacyuptime ? (
                <PharmacyInfo pharmacy={pharma} />
              ) : (
                <View
                  style={{
                    height: "100%",
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                >
                  <ActivityIndicator color={COLORSS.Green} size={"small"} />
                </View>
              )}
            </View>
          </BottomSheetScrollView>
        </>
      ) : null}
      {stockscreen ? (
        <>{Stock ? <PharmacyProductsList stock={Stock || []} /> : null}</>
      ) : null}
      {cartscreen ? (
        <>
          <BottomSheetScrollView
            contentContainerStyle={{ alignItems: "center" }}
          >
            {cart.map((item) => {
              return (
                <View style={styles.productitem} key={item.product.id}>
                  <View
                    style={{
                      flexDirection: "row",
                      alignItems: "center",
                      width: "80%",
                    }}
                  >
                    <View
                      style={{
                        height: "90%",
                        width: "25%",
                        borderRadius: 20,
                      }}
                    >
                      <Image
                        style={{
                          height: "100%",
                          width: "100%",
                          resizeMode: "contain",
                        }}
                        source={{ uri: item.product.picture }}
                      ></Image>
                    </View>
                    <View>
                      <Text
                        style={{
                          fontSize: 14,
                          fontWeight: "600",
                          marginLeft: 10,
                        }}
                      >
                        {item.product.name}
                      </Text>
                    </View>
                  </View>

                  <TouchableOpacity
                    onPress={() => {
                      deleteitem(item);
                    }}
                  >
                    <MaterialIcons
                      name="delete-outline"
                      size={20}
                      color={COLORSS.Green}
                    />
                  </TouchableOpacity>
                </View>
              );
            })}
            <View style={{}}></View>
          </BottomSheetScrollView>
          <View style={styles.CartView}>
            <TouchableOpacity
              onPress={() => {
                if (pharma) {
                  router.push({
                    pathname: "/Screens/Cart",
                    params: pharma,
                  });
                }
              }}
              style={styles.CartButton}
            >
              <Feather name="shopping-cart" size={24} color="white" />
            </TouchableOpacity>
          </View>
        </>
      ) : null}
    </BottomSheet>
  );
}

const styles = StyleSheet.create({
  contentContainer: {
    flex: 1,
    backgroundColor: COLORSS.white,
    // alignItems: "center",
  },
  CartView: {
    height: 60,
    justifyContent: "center",
    alignItems: "flex-end",
    paddingRight: 20,
  },
  PharmacyView: {
    width: "100%",
    // borderWidth: 1,
    // borderColor: "black",
    justifyContent: "center",
    alignItems: "flex-start",
    marginTop: 30,
    padding: 10,
  },
  CartButton: {
    height: 40,
    width: 70,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 9,
    backgroundColor: COLORSS.Green,
  },
  pharmacyIcon: {
    height: 100,
    width: 100,
    backgroundColor: "white",
    borderRadius: 70,
    position: "absolute",
    justifyContent: "center",
    alignItems: "center",
    top: 110,
    left: 40,
    ...SHADOWS.small,
  },
  Pharmacypicture: {
    width: "100%",
    height: 180,
    resizeMode: "cover",
  },

  BigTitle: {
    fontSize: 18,
    fontWeight: "800",
  },
  Smalltext: {
    fontSize: 14,
    fontWeight: "500",
    color: "gray",
  },
  pharmaContent: {
    justifyContent: "space-evenly",
    alignItems: "flex-start",
    width: "95%",
    // borderWidth: 2,
    // borderColor: "green",
  },
  row: {
    display: "flex",
    justifyContent: "space-evenly",
    paddingTop: 5,
    paddingBottom: 5,
  },
  productstitle: {
    fontSize: 20,
    fontWeight: "600",
    marginBottom: 10,
    marginLeft: 20,
  },
  sheetButton: {
    justifyContent: "flex-start",
    alignItems: "center",
    flexDirection: "row",
    height: 70,
  },
  Buttonscreen: {
    backgroundColor: COLORSS.maingray,
    paddingVertical: 10,
    paddingHorizontal: 10,
    borderRadius: 10,
    marginLeft: 10,
    justifyContent: "center",
    alignItems: "center",
  },
  Textscreen: {
    fontSize: 16,
    fontWeight: "bold",
  },
  productitem: {
    height: 70,
    flexDirection: "row",
    alignItems: "center",
  },
});
