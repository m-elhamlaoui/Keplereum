import axios from "axios";

type Validator = {
  address: string;
  privateKey: string;
  name?: string;
};

const ALERT_API_URL = "http://localhost:8222/api/v1/blockchain/contract/alert";
const ALERT_INTERVAL_MS = 60000;

const ALERT_TYPES = ["FIRE", "TORNADO", "HURRICANE", "TSUNAMI"];
const LOCATIONS = [
    { latitude: 37.7749 + Math.random(), longitude: -122.4194 + Math.random() },
    { latitude: 40.7128 + Math.random(), longitude: -74.0060 + Math.random() },
    { latitude: 51.5074 + Math.random(), longitude: -0.1278 + Math.random() },
    { latitude: 48.8566 + Math.random(), longitude: 2.3522 + Math.random() },
];

function getRandomElement<T>(arr: T[]): T {
  return arr[Math.floor(Math.random() * arr.length)];
}

function alertExists(alerts: any[], latitude: number, longitude: number): boolean {
  return alerts.some(
    (alert) =>
      Number(alert.latitude) === Number(latitude) &&
      Number(alert.longitude) === Number(longitude)
  );
}

export function startAlertTransactions(validators: Validator[], alerts: any[]) {
  if (!validators || validators.length === 0) {
    console.warn("No validators available for alert submission.");
    return;
  }

  let idx = 0;

  setInterval(async () => {
    const validator = validators[idx % validators.length];
    const alertType = getRandomElement(ALERT_TYPES);
    const loc = getRandomElement(LOCATIONS);

    const location = {
      latitude: Math.round(loc.latitude * 1e6), // Convert to BigInteger-compatible value
      longitude: Math.round(loc.longitude * 1e6), // Convert to BigInteger-compatible value
    };

    if (alertExists(alerts, location.latitude, location.longitude)) {
      console.log(`[AlertTx] Alert already exists at (${location.latitude}, ${location.longitude}), skipping.`);
      idx++;
      return;
    }

    try {
      const response = await axios.post(ALERT_API_URL, null, {
        params: {
          privateKey: validator.privateKey,
          alertType,
          latitude: location.latitude, // Send scaled latitude
          longitude: location.longitude, // Send scaled longitude
        },
      });
      console.log(
        `[AlertTx] Submitted alert (${alertType}) from ${validator.name || validator.address}:`,
        response.data
      );
    } catch (error: any) {
      console.error(
        `[AlertTx] Failed to submit alert from ${validator.name || validator.address}:`,
        error?.response?.data || error.message
      );
    }

    idx++;
  }, ALERT_INTERVAL_MS);
}