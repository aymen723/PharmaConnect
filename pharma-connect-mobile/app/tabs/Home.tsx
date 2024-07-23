import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  ScrollView,
  ActivityIndicator,
} from "react-native";
import React, { useCallback, useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { COLORSS, Gstyles } from "../constants/theme";
import Productcard from "../Component/Productcard";
import { DrawerActions } from "@react-navigation/native";
import { router } from "expo-router";
import { useNavigation } from "@react-navigation/core";
import { fetchProductsByFilter } from "../client/api/stockService/productApi";
import { fetchTagsByFilter } from "../client/api/stockService/tagApi";
import Tag from "../Component/Tag";
import { Page } from "../client/types/responses";
import {
  ProductRespData,
  TagRespData,
} from "../client/types/responses/StockResponses";
import Feather from "@expo/vector-icons/Feather";
import Entypo from "@expo/vector-icons/Entypo";

export default function Home() {
  const [Products, setProducts] = useState<ProductRespData[]>([]);
  const [Tags, setTags] = useState<Page<TagRespData> | undefined>();
  const navigation = useNavigation();
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [loadingMore, setLoadingMore] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [selectedTag, setselectedTag] = useState<TagRespData | undefined>();

  const fetchProducts = useCallback(async () => {
    if (loadingMore || !hasMore) return;

    setLoadingMore(true);
    try {
      const res = await fetchProductsByFilter({
        page,
        tags: selectedTag?.id,
      });
      setProducts((prevProducts) => [...prevProducts, ...res.data.content]);
      setHasMore(res.data.content.length > 0);
    } catch (err) {
      console.error("Error fetching products", err);
    } finally {
      setLoadingMore(false);
    }
  }, [page, loadingMore, hasMore, selectedTag]);

  useEffect(() => {
    setLoading(true);
    fetchProductsByFilter({ tags: selectedTag?.id, page: 0 })
      .then((res) => {
        setProducts(res.data.content);
        setHasMore(res.data.content.length > 0);
        console.log(res.data.content);
      })
      .catch((err) => {
        console.error("Error fetching initial products", err);
      })
      .finally(() => {
        setLoading(false);
      });

    fetchTagsByFilter()
      .then((res) => {
        setTags(res.data);
      })
      .catch((err) => {
        console.error("Error fetching tags", err);
      });
  }, [selectedTag]);

  const handleLoadMore = () => {
    if (!loadingMore && hasMore) {
      setPage((prevPage) => prevPage + 1);
      fetchProducts();
    }
  };
  return (
    <View style={Gstyles.container}>
      <StatusBar backgroundColor={"white"}></StatusBar>
      <View style={styles.searchbox}>
        <View style={styles.inputView}>
          <TouchableOpacity
            onPress={() => {
              router.push("/Screens");
            }}
            style={Gstyles.searchinput}
          >
            <Feather
              name="search"
              size={20}
              style={{ paddingLeft: 20 }}
              color="gray"
            />
            <Text style={{ paddingLeft: 10, color: "gray" }}>Search</Text>
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() => {
              router.push("/Screens/Cart");
            }}
            style={styles.cart}
          >
            <Feather name="shopping-cart" size={24} color="black" />
          </TouchableOpacity>
        </View>
      </View>

      <View style={styles.productbox}>
        <View style={styles.TagContainer}>
          <View style={{ width: "20%", justifyContent: "center" }}>
            <TouchableOpacity
              onPress={() => {
                setselectedTag(undefined);
              }}
              style={styles.TagView}
            >
              <Text style={styles.TagText}>Tous</Text>
            </TouchableOpacity>
          </View>
          <ScrollView
            horizontal={true}
            style={{
              width: "85%",
              height: "100%",
            }}
            contentContainerStyle={{ alignItems: "center" }}
            showsHorizontalScrollIndicator={false}
          >
            {Tags ? (
              Tags.content.map((item) => {
                return (
                  <TouchableOpacity
                    onPress={() => {
                      setselectedTag(item);
                    }}
                    key={item.id}
                  >
                    <Tag key={item.id} item={item}></Tag>
                  </TouchableOpacity>
                );
              })
            ) : (
              <View
                style={{
                  width: 400,
                  height: "100%",
                  justifyContent: "center",
                }}
              >
                <ActivityIndicator
                  color="green"
                  size={"large"}
                ></ActivityIndicator>
              </View>
            )}
          </ScrollView>
        </View>
        <View style={styles.productlist}>
          <FlatList
            style={{ backgroundColor: COLORSS.white }}
            contentContainerStyle={styles.listscroll}
            data={Products}
            showsHorizontalScrollIndicator={false}
            showsVerticalScrollIndicator={false}
            overScrollMode="never"
            numColumns={2}
            // keyExtractor={(item) => item.id}
            columnWrapperStyle={styles.row}
            renderItem={({ item }) => <Productcard item={item} />} // pagingEnabled={true}
            onEndReached={handleLoadMore}
            onEndReachedThreshold={0.5}
            ListFooterComponent={
              loadingMore && <ActivityIndicator size="large" color="green" />
            }
          />
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  searchbox: {
    flex: 0.1,
    // borderColor: "black",
    // borderWidth: 1,
    backgroundColor: "white",
  },
  productbox: {
    flex: 0.9,

    alignItems: "center",
  },
  productlist: {
    flex: 1,
    width: "100%",
    // borderWidth: 1,
    // borderColor: "red",
  },
  TagContainer: {
    width: "100%",
    flex: 0.1,
    backgroundColor: "white",
    flexDirection: "row",
  },
  listscroll: {
    width: "100%",
  },
  row: {
    display: "flex",
    justifyContent: "space-evenly",
    paddingTop: 5,
    paddingBottom: 5,
  },
  inputView: {
    height: "100%",
    // borderColor: "red",
    // borderWidth: 1,
    width: "100%",
    display: "flex",
    justifyContent: "space-evenly",
    alignItems: "center",
    flexDirection: "row",
  },
  sidebar: {
    width: "10%",
    height: 40,
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 50,
    backgroundColor: "ghostwhite",
  },
  cart: {
    width: "10%",
    height: 40,
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 50,
    backgroundColor: "ghostwhite",
  },
  TagView: {
    backgroundColor: COLORSS.maingray,
    padding: 10,
    paddingHorizontal: 15,
    borderRadius: 15,
    marginLeft: 5,
    marginRight: 5,
  },
  TagText: {
    fontSize: 16,
    fontWeight: "bold",

    color: COLORSS.Green,
  },
});
