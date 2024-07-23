import React from "react";
import { COLORSS, Gstyles } from "../constants/theme";
import Entypo from "@expo/vector-icons/Entypo";
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  useWindowDimensions,
  View,
  ViewToken,
} from "react-native";
import Animated, {
  interpolate,
  SharedValue,
  useAnimatedRef,
  useAnimatedScrollHandler,
  useAnimatedStyle,
  useSharedValue,
} from "react-native-reanimated";
import { Pagination } from "./OnboardingComp/Pagination";

import { data, type Data } from "../Component/Onboarding";
import { router } from "expo-router";

const RenderItem = ({
  item,
  index,
  x,
}: {
  item: Data;
  index: number;
  x: SharedValue<number>;
}) => {
  const { width: SCREEN_WIDTH } = useWindowDimensions();

  const imageAnimatedStyle = useAnimatedStyle(() => {
    const opacityAnimation = interpolate(
      x.value,
      [
        (index - 1) * SCREEN_WIDTH,
        index * SCREEN_WIDTH,
        (index + 1) * SCREEN_WIDTH,
      ],
      [0, 1, 0]
    );

    const translateYAnimation = interpolate(
      x.value,
      [
        (index - 1) * SCREEN_WIDTH,
        index * SCREEN_WIDTH,
        (index + 1) * SCREEN_WIDTH,
      ],
      [100, 0, 100]
    );

    return {
      width: SCREEN_WIDTH * 0.8,
      height: SCREEN_WIDTH * 0.8,
      opacity: opacityAnimation,
      transform: [{ translateY: translateYAnimation }],
    };
  });

  const textAnimatedStyle = useAnimatedStyle(() => {
    const opacityAnimation = interpolate(
      x.value,
      [
        (index - 1) * SCREEN_WIDTH,
        index * SCREEN_WIDTH,
        (index + 1) * SCREEN_WIDTH,
      ],
      [0, 1, 0]
    );

    const translateYAnimation = interpolate(
      x.value,
      [
        (index - 1) * SCREEN_WIDTH,
        index * SCREEN_WIDTH,
        (index + 1) * SCREEN_WIDTH,
      ],
      [100, 0, 100]
    );

    return {
      opacity: opacityAnimation,
      transform: [{ translateY: translateYAnimation }],
    };
  });

  return (
    <View style={[styles.itemContainer, { width: SCREEN_WIDTH }]}>
      <Animated.Image source={item.image} style={imageAnimatedStyle} />

      <Animated.View style={textAnimatedStyle}>
        <Text style={styles.itemTitle}>{item.title}</Text>
        <Text style={styles.itemText}>{item.text}</Text>
      </Animated.View>
    </View>
  );
};

export default function Onboarding() {
  const { width: SCREEN_WIDTH } = useWindowDimensions();
  const flatListRef = useAnimatedRef<FlatList>();

  const flatListIndex = useSharedValue(0);
  const x = useSharedValue(0);

  const onViewableItemsChanged = ({
    viewableItems,
  }: {
    viewableItems: Array<ViewToken>;
  }) => {
    flatListIndex.value = viewableItems[0].index ?? 0;
  };

  const onScroll = useAnimatedScrollHandler({
    onScroll: (event) => {
      x.value = event.contentOffset.x;
    },
  });
  return (
    <View style={[Gstyles.container, { backgroundColor: "white" }]}>
      <View style={Styles.OnboardConatiner}>
        <Animated.FlatList
          ref={flatListRef as any}
          data={data}
          keyExtractor={(item) => String(item.id)}
          renderItem={({ item, index }) => (
            <RenderItem index={index} item={item} x={x} />
          )}
          onScroll={onScroll}
          scrollEventThrottle={16}
          horizontal
          showsHorizontalScrollIndicator={false}
          bounces={false}
          pagingEnabled
          onViewableItemsChanged={onViewableItemsChanged}
        />
      </View>
      <View style={Styles.FooterContainer}>
        <TouchableOpacity
          onPress={() => {
            router.push("/(auth)/Signin");
          }}
          style={[Styles.FooterButton, { backgroundColor: COLORSS.Green }]}
        >
          <Text
            style={{
              color: "white",
              fontWeight: "600",
              letterSpacing: 1,
              fontSize: 15,
            }}
          >
            Skip
          </Text>
        </TouchableOpacity>
        <View style={Styles.Pages}>
          <Pagination data={data} screenWidth={SCREEN_WIDTH} x={x} />
        </View>
        <TouchableOpacity
          onPress={() => {
            if (flatListIndex.value + 1 === data.length) {
              router.push("(auth)/Signin");
            }
          }}
          style={Styles.FooterButton}
        >
          <Entypo name="chevron-right" size={30} color={COLORSS.Green} />
        </TouchableOpacity>
      </View>
    </View>
  );
}

export const theme = {
  colors: {
    backgroundColor: "white",
    backgroundHighlightColor: COLORSS.Green,
    textColor: "#1b1b1b",
    textHighlightColor: "#f0f0f0",
  },
};

const Styles = StyleSheet.create({
  OnboardConatiner: {
    flex: 0.9,
  },
  FooterContainer: {
    flex: 0.1,

    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
  },
  FooterButton: {
    width: 80,
    height: 40,

    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLORSS.maingray,
    borderRadius: 15,
  },
  Pages: {
    width: "30%",
    alignItems: "center",
  },
});

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.backgroundColor,
  },
  itemContainer: {
    flex: 1,
    backgroundColor: theme.colors.backgroundColor,
    alignItems: "center",
    justifyContent: "space-around",
  },
  itemTitle: {
    color: theme.colors.textColor,
    fontSize: 22,
    fontWeight: "bold",
    textAlign: "center",
    marginBottom: 10,
  },
  itemText: {
    color: theme.colors.textColor,
    textAlign: "center",
    lineHeight: 20,
    marginHorizontal: 30,
  },
  footerContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    margin: 20,
  },
});
