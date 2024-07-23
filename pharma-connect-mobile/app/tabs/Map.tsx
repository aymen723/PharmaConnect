import {
  View,
  StyleSheet,
  ActivityIndicator,
  TextInput,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import { Gstyles, SHADOWS } from "../constants/theme";
import MapView, { Marker, PROVIDER_GOOGLE, Region } from "react-native-maps";
import * as Location from "expo-location";
import { LocationObject } from "expo-location";
import {
  fetchPharmaciesByFilter,
  fetchPharmacyById,
} from "../client/api/stockService/pharmacyApi";
import { PharmacyRespData } from "../client/types/responses/StockResponses";
import { PharmacyFilterParams } from "../client/types/requests/PharmacyRequests";
import { Page } from "../client/types/responses";
import MapHeader from "../Component/MapHeader";
import { Button } from "@rneui/base";
import FilterModal from "../Component/FilterModal";
import { Text } from "react-native";
import { useWindowDimensions } from "react-native";
import { Entypo } from "@expo/vector-icons";
export default function Map() {
  const { height, width } = useWindowDimensions();
  const [loc, setLocation] = useState<LocationObject | undefined>();
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [region, setregion] = useState<Region | undefined>();
  const [Pharmcaies, SetPharmacies] = useState<
    Page<PharmacyRespData> | undefined
  >();
  const [Range, SetRange] = useState(0);
  const [isModalVisible, setModalVisible] = useState<boolean>(false);
  const [Search, SetSearch] = useState<string | undefined>("");
  const [Pharmacy, SetPharmacy] = useState<PharmacyRespData | undefined>();
  const handleModalToggle = () => {
    setModalVisible(!isModalVisible);
  };

  function Regionchange(e: Region) {
    setregion(e);
  }

  function HundelPharmcay(item: PharmacyRespData) {
    console.log(item);
    fetchPharmacyById(item.id).then((res) => {
      console.log(res.data);
      SetPharmacy(res.data);
    });
  }

  async function GetLocation() {
    const { status } = await Location.requestForegroundPermissionsAsync();
    if (status !== "granted") {
      setErrorMsg("Permission to access location was denied");
      return;
    }

    const location = await Location.getCurrentPositionAsync({});
    console.log(location);
    console.log(location.coords.altitude);
    console.log(location.coords.longitude);
    setLocation(location);
  }

  useEffect(() => {
    console.log("height", height);

    fetchPharmaciesByFilter({ name: Search })
      .then((res) => {
        console.log(res.data);
        console.log("here the lenght", res.data.numberOfElements);
        SetPharmacies(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [Search]);

  useEffect(() => {
    GetLocation();
  }, []);

  return (
    <>
      {loc ? (
        <View style={[Gstyles.container, { alignItems: "center" }]}>
          <>
            <View style={styles.headersearch}>
              <TextInput
                style={[
                  Gstyles.searchinput,
                  { fontSize: 16, width: "75%", padding: 10 },
                ]}
                onChangeText={(e) => SetSearch(e)}
                value={Search}
                placeholder="Pharamcies"
                keyboardType="web-search"
              ></TextInput>
              <TouchableOpacity
                onPress={() => {
                  handleModalToggle();
                }}
                style={styles.menubutton}
              >
                <Entypo name="menu" size={24} color="black" />
              </TouchableOpacity>
            </View>
          </>
          <MapView
            initialRegion={{
              latitude: loc.coords.latitude,
              longitude: loc.coords.longitude,
              latitudeDelta: 0.1,
              longitudeDelta: 0.1,
            }}
            region={region}
            onRegionChangeComplete={(e) => {
              Regionchange(e);
              // console.log(e);
            }}
            style={styles.map}
            mapPadding={{ top: height - 120, right: 0, bottom: 0, left: 0 }}
            provider={PROVIDER_GOOGLE}
            showsUserLocation
            showsMyLocationButton
            showsBuildings
            mapType={"standard"}
          >
            {Pharmcaies
              ? Pharmcaies.content.map((item: PharmacyRespData) => {
                  return (
                    <Marker
                      coordinate={{
                        latitude: item.location.coordinates.y,
                        longitude: item.location.coordinates.x,
                      }}
                      key={item.id}
                      onPress={() => {
                        HundelPharmcay(item);
                      }}
                      title={item.name}
                      icon={require("../../assets/Images/download.png")}
                    ></Marker>
                  );
                })
              : null}
          </MapView>
          <></>

          <FilterModal
            Range={SetRange}
            Visible={setModalVisible}
            Statevisible={isModalVisible}
          ></FilterModal>
          <View style={Pharmcystyles.container}>
            <Text>azdadza</Text>
          </View>
        </View>
      ) : (
        <View
          style={[
            Gstyles.container,
            { justifyContent: "center", alignItems: "center" },
          ]}
        >
          <ActivityIndicator color="green" size={"large"}></ActivityIndicator>
        </View>
      )}
    </>
  );
}

const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: "100%",
  },
  headersearch: {
    position: "absolute",
    width: "90%",
    borderRadius: 20,
    height: 60,
    backgroundColor: "white",
    top: 20,
    left: 20,
    zIndex: 1,
    flexDirection: "row-reverse",
    justifyContent: "space-evenly",
    alignItems: "center",
    ...SHADOWS.medium,
  },
  menubutton: {
    height: 50,
    width: 50,
    borderRadius: 50,

    backgroundColor: "white",
    zIndex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});

const Pharmcystyles = StyleSheet.create({
  container: {
    width: "85%",
    height: 200,
    borderRadius: 30,
    backgroundColor: "white",
    elevation: 10,
  },
});
