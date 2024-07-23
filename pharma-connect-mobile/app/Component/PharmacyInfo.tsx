import React from "react";
import { View, Text, StyleSheet, Image } from "react-native";

type WeekDay =
  | "Monday"
  | "Tuesday"
  | "Wednesday"
  | "Thursday"
  | "Friday"
  | "Saturday"
  | "Sunday";

type UptimeRespData = {
  id: number;
  openTime: string;
  closeTime: string;
  day: WeekDay;
};

type PharmacyUptime = {
  uptimes?: UptimeRespData[];
  open: boolean;
};

type PharmacyRespData = {
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

type PharmacyInfoProps = {
  pharmacy: PharmacyRespData;
};

const PharmacyInfo: React.FC<PharmacyInfoProps> = ({ pharmacy }) => {
  return (
    <View style={styles.container}>
      {pharmacy.picture && (
        <Image source={{ uri: pharmacy.picture }} style={styles.image} />
      )}
      <Text style={styles.name}>{pharmacy.name}</Text>

      <Text style={styles.detail}>Phone: {pharmacy.phoneNumber || "N/A"}</Text>
      <Text style={styles.detail}>
        Supports Payment: {pharmacy.supportPayment ? "Yes" : "No"}
      </Text>
      <Text style={styles.detail}>
        Enabled: {pharmacy.enabled ? "Yes" : "No"}
      </Text>
      <Text style={styles.detail}>
        Currently Open: {pharmacy.upTimes.open ? "Yes" : "No"}
      </Text>
      {pharmacy.upTimes.uptimes && pharmacy.upTimes.uptimes.length > 0 && (
        <View style={styles.uptimeContainer}>
          <Text style={styles.uptimeHeader}>Uptimes:</Text>
          {pharmacy.upTimes.uptimes.map((uptime) => (
            <View key={uptime.id} style={styles.uptimeDetail}>
              <Text style={styles.uptimeDay}>{uptime.day}:</Text>
              <Text style={styles.uptimeTime}>
                {uptime.openTime} - {uptime.closeTime}
              </Text>
            </View>
          ))}
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 16,
    backgroundColor: "#fff",
    borderRadius: 8,
    shadowColor: "#000",
    shadowOpacity: 0.1,
    shadowRadius: 10,
    elevation: 5,
    margin: 16,
  },
  image: {
    width: "100%",
    height: 200,
    borderRadius: 8,
  },
  name: {
    fontSize: 24,
    fontWeight: "bold",
    marginVertical: 8,
  },
  detail: {
    fontSize: 16,
    marginVertical: 4,
  },
  uptimeContainer: {
    marginTop: 16,
  },
  uptimeHeader: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 8,
  },
  uptimeDetail: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginVertical: 4,
    padding: 8,
    backgroundColor: "#f9f9f9",
    borderRadius: 8,
  },
  uptimeDay: {
    fontSize: 16,
    fontWeight: "600",
  },
  uptimeTime: {
    fontSize: 16,
    color: "#555",
  },
});

export default PharmacyInfo;
