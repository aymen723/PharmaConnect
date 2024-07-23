import {
  FlatList,
  View,
  Text,
  ScrollView,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import { StatusBar } from "expo-status-bar";
import SearchHeader from "../Component/SearchHeader";
import { fetchProductsByFilter } from "../client/api/stockService/productApi";
import {
  ProductRespData,
  TagRespData,
} from "../client/types/responses/StockResponses";
import { fetchTagsByFilter } from "../client/api/stockService/tagApi";
import { Page } from "../client/types/responses";
import Tag from "../Component/Tag";
import SearchproductComp from "../Component/SearchproductComp";
import { router } from "expo-router";
import { Feather } from "@expo/vector-icons";
export default function Search() {
  const [Search, SetSearch] = useState<string>("");
  const [Products, SetProducts] = useState<Page<ProductRespData> | undefined>();
  const [productslist, setProductslist] = useState<
    ProductRespData[] | undefined
  >();
  const [Tags, SetTags] = useState();
  const [listSearch, setlistSearch] = useState<ProductRespData[] | []>([]);

  useEffect(() => {
    fetchProductsByFilter({ search: Search, tags: Tags })
      .then((res) => {
        console.log(
          "here in new is the list in Search Screen",
          res.data.totalElements
        );
        SetProducts(res.data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, [Search, Tags]);
  return (
    <View style={Gstyles.container}>
      <StatusBar backgroundColor={"white"}></StatusBar>
      <SearchHeader
        SearchValue={Search}
        SearchInput={SetSearch}
        TagSelected={SetTags}
        ListProducts={listSearch}
        setListProducts={setlistSearch}
      ></SearchHeader>
      {(!!Search.length || !!Tags) && (
        <ScrollView
          contentContainerStyle={{
            alignItems: "center",
          }}
        >
          {Products?.content.map((item) => {
            return (
              <TouchableOpacity
                key={item.id}
                onPress={() => {
                  router.push({
                    pathname: `/Screens/ProductDescription`,
                    params: {
                      id: item.id,
                      name: item.name,
                    },
                  });
                }}
                style={styles.container}
              >
                <SearchproductComp item={item} />
                <TouchableOpacity
                  onPress={() => {
                    if (!listSearch.includes(item)) {
                      setlistSearch((prevState) => [item, ...prevState]);
                    }
                  }}
                  style={{
                    justifyContent: "center",
                    alignItems: "center",
                    width: "25%",
                  }}
                >
                  <Feather name="plus-square" size={24} color={COLORSS.Green} />
                </TouchableOpacity>
              </TouchableOpacity>
            );
          })}
        </ScrollView>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "90%",
    height: 70,
    flexDirection: "row",
    borderBottomColor: "lightgray",
    borderBottomWidth: 0.75,
    marginBottom: 5,
    marginTop: 5,
  },
});
