import { Dimensions, StyleSheet } from "react-native";
export const deviceWidth = Dimensions.get("window").width;

const COLORSS = {
  primary: "#312651",
  secondary: "#444262",
  tertiary: "#FF7754",
  gray: "#83829A",
  gray2: "#C1C0C8",
  maingray: "#f5f5fd",
  white: "white",
  lightWhite: "#FAFAFC",
  Green: "#15BD92",
  lightgreen: "#95d5b2",
  textcolor: "#090F47",
  purpal: "#4157FF",
};

const FONT = {
  regular: "DMRegular",
  medium: "DMMedium",
  bold: "DMBold",
};

const SIZES = {
  xSmall: 10,
  small: 12,
  medium: 16,
  large: 20,
  xLarge: 24,
  xxLarge: 32,
};

const SHADOWS = {
  small: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 2,
  },
  medium: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 5.84,
    elevation: 5,
  },
};

export const Gstyles = StyleSheet.create({
  Button: {
    alignItems: "center",
    justifyContent: "center",
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 5,
    elevation: 3,
  },
  Buttontitle: {
    fontSize: 14,
    lineHeight: 21,
    fontWeight: "bold",
    letterSpacing: 0.25,
    color: "white",
  },
  searchinput: {
    width: "70%",
    height: 45,
    backgroundColor: "ghostwhite",
    borderRadius: 20,
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-start",
    flexDirection: "row",
  },
  container: {
    flex: 1,
    backgroundColor: COLORSS.maingray,
    paddingTop: 30,
  },
  whitecontainer: {
    flex: 1,
    backgroundColor: "white",
  },
  whitecontainercenter: {
    flex: 1,
    backgroundColor: "white",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  loginbutton: {
    alignItems: "center",
    justifyContent: "center",
    width: "80%",
    height: 50,
    borderRadius: 10,
    elevation: 3,
    backgroundColor: COLORSS.Green,
  },
  headerContainer: {
    height: 60,
    width: "100%",

    backgroundColor: COLORSS.white,
    flexDirection: "row",
    justifyContent: "space-between",
  },
  Mapheader: {
    height: 60,
    backgroundColor: COLORSS.maingray,
    opacity: 0.7,

    zIndex: 5,
    justifyContent: "space-evenly",
    alignItems: "center",
    flexDirection: "row",
  },
  BigButton: {
    width: "80%",
    height: 45,
    backgroundColor: COLORSS.purpal,
    borderRadius: 20,
    justifyContent: "center",
    alignItems: "center",
    ...SHADOWS.small,
  },
  BigButtonText: {
    color: "white",
    fontSize: 16,
    fontWeight: "bold",
  },
});

const Tagcolors = [
  {
    id: 1,
    name: "collyre",
    color: "#15BD92",
  },
  {
    id: 2,
    name: "insulin",
    color: "#E679A3",
  },
  {
    id: 3,
    name: "oral",
    color: "#FFB347",
  },
  {
    id: 4,
    name: "200ml",
    color: "#6A5ACD",
  },
  {
    id: 5,
    name: "30ml",
    color: "#FFD700",
  },
  {
    id: 6,
    name: "pilules",
    color: "#FF7F50",
  },
  {
    id: 7,
    name: "liquide",
    color: "#4682B4",
  },
  {
    id: 8,
    name: "comprimée",
    color: "#8A2BE2",
  },
  {
    id: 9,
    name: "10mg",
    color: "#20B2AA",
  },
  {
    id: 10,
    name: "solide",
    color: "#BC8F8F",
  },
];

export { Tagcolors, COLORSS, FONT, SIZES, SHADOWS };
