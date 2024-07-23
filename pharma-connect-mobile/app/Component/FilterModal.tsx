import { View, Text, StyleSheet } from "react-native";
import React, { useState } from "react";
import { COLORSS, SHADOWS, deviceWidth } from "../constants/theme";
import { Slider } from "@rneui/themed";
import { AntDesign, MaterialIcons } from "@expo/vector-icons";
import Modal from "react-native-modal";
import { Dropdown } from "react-native-element-dropdown";

export const Rangedata = [
  { label: "1Km", value: "1000" },
  { label: "2Km", value: "2000" },
  { label: "3km", value: "3000" },
  { label: "4Km", value: "4000" },
  { label: "5Km", value: "5000" },
  { label: "6Km", value: "6000" },
  { label: "7Km", value: "7000" },
  { label: "8Km", value: "8000" },
];
export default function FilterModal({ Visible, Range, Statevisible }) {
  const [RangeSlider, SetRange] = useState(0);
  const [isModalVisible, setModalVisible] = useState<boolean>(false);
  const [value, setValue] = useState(null);
  function hundelRange(e) {
    SetRange(e);
    console.log(e);
    Range(e);
  }
  function HundelModal() {
    setModalVisible(false);
    Visible(false);
  }
  return (
    <Modal
      deviceWidth={deviceWidth}
      style={Modalstyles.Modal}
      isVisible={Statevisible}
      backdropOpacity={0.1}
      hasBackdrop={true}
      // onSwipeComplete={() => setModalVisible(false)}
      onBackButtonPress={() => HundelModal()}
      onBackdropPress={() => HundelModal()}
    >
      <View style={Modalstyles.container}>
        <View
          style={{
            height: 4,
            width: 60,
            backgroundColor: COLORSS.Green,
            borderRadius: 10,
            margin: 15,
          }}
        ></View>

        <View style={Modalstyles.input}>
          <Text style={{ fontSize: 16, fontWeight: "bold", color: "gray" }}>
            Range
          </Text>
          <Dropdown
            style={Styles.dropdown}
            placeholderStyle={Styles.placeholderStyle}
            selectedTextStyle={Styles.selectedTextStyle}
            inputSearchStyle={Styles.inputSearchStyle}
            iconStyle={Styles.iconStyle}
            data={Rangedata}
            search
            maxHeight={300}
            labelField="label"
            valueField="value"
            placeholder="Select item"
            searchPlaceholder="Search..."
            value={value}
            onChange={(item) => {
              setValue(item.value);
            }}
            renderLeftIcon={() => (
              <AntDesign
                style={Styles.icon}
                color="black"
                name="Safety"
                size={20}
              />
            )}
          />
        </View>
      </View>
    </Modal>
  );
}
const Modalstyles = StyleSheet.create({
  container: {
    width: deviceWidth,
    height: 400,
    backgroundColor: "white",
    borderTopRightRadius: 40,
    borderTopLeftRadius: 40,
    // justifyContent: "center",
    alignItems: "center",
  },
  Modal: {
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
    margin: 0,
  },
  input: {
    width: "100%",
    height: 70,
    justifyContent: "space-evenly",
    alignItems: "center",
    flexDirection: "row",
  },
  slider: {
    // color: "black",
    // backgroundColor: "gray",
    width: "75%",
  },
});

const Styles = StyleSheet.create({
  dropdown: {
    margin: 16,
    height: 50,
    borderBottomColor: "gray",
    borderBottomWidth: 0.5,
    width: "70%",
  },
  icon: {
    marginRight: 5,
  },
  placeholderStyle: {
    fontSize: 16,
  },
  selectedTextStyle: {
    fontSize: 16,
  },
  iconStyle: {
    width: 20,
    height: 20,
  },
  inputSearchStyle: {
    height: 40,
    fontSize: 16,
  },
});
