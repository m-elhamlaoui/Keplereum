import axios from "axios";

type Validator = {
  address: string;
  privateKey: string;
  name?: string;
};

const ALERT_API_URL = "http://localhost:8222/api/v1/blockchain/contract/alert";
const ALERT_INTERVAL_MS = 25000;

const ALERT_TYPES = ["FIRE", "TORNADO", "HURRICANE", "TSUNAMI"];
const LOCATIONS = [
  { latitude: Math.floor(37 + Math.random() * 10), longitude: Math.floor(122 + Math.random() * 10) },
  { latitude: Math.floor(40 + Math.random() * 10), longitude: Math.floor(74 + Math.random() * 10) },
  { latitude: Math.floor(51 + Math.random() * 10), longitude: Math.floor(0 + Math.random() * 10) },
  { latitude: Math.floor(48 + Math.random() * 10), longitude: Math.floor(2 + Math.random() * 10) },
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

export function startAlertTransactions(
  validators: Validator[],
  alerts: any[],
  setPendingTransactions: (pendingTransactions: any[]) => void,
  pendingTransactions: any[],
) {
  if (!validators || validators.length === 0) {
    console.warn("No validators available for alert submission.");
    return;
  }

  let idx = 0;

  setInterval(async () => {
    const validator = validators[idx % validators.length];
    const alertType = getRandomElement(ALERT_TYPES);
    const loc = getRandomElement(LOCATIONS);

    try {
      const location = {
        latitude: loc.latitude,
        longitude: loc.longitude,
      };

      if (alertExists(alerts, location.latitude, location.longitude)) {
        console.log(`[AlertTx] Alert already exists at (${location.latitude}, ${location.longitude}), skipping.`);
        idx++;
        return;
      }

      const response = await axios.post(ALERT_API_URL, null, {
        params: {
          privateKey: validator.privateKey,
          alertType,
          latitude: location.latitude,
          longitude: location.longitude,
        },
      });

      console.log(
        `[AlertTx] Submitted alert (${alertType}) from ${validator.name || validator.address}:`,
        response.data
      );

      if (response.data) {
        // Fetch transaction details using the hash from the alert submission response
        try {
          const txResponse = await axios.get(`http://localhost:8222/api/v1/blockchain/transaction`, {
            params: { hash: response.data },
          });

          const hashAsciiDecimal = txResponse.data.transaction.hash
            .split('')
            .reduce((sum: number, char: string) => sum + char.charCodeAt(0), 0)
            .toString();
            
          const truncateHash = (hash: string) =>
            hash.length > 16 ? `${hash.slice(0, 14)}...` : hash;

            setPendingTransactions([
            {
              id: hashAsciiDecimal,
              hash: `${txResponse.data.transaction.hash.slice(0, 20)}...`,
              from: truncateHash(txResponse.data.transaction.from),
              to: truncateHash(txResponse.data.transaction.to),
              amount: parseFloat(txResponse.data.transaction.value) / 1e18 || 0,
              fee: (parseInt(txResponse.data.transaction.gas, 16) * parseInt(txResponse.data.transaction.gasPrice, 16)) / 1e18 || 0,
              status: txResponse.data.transaction.blockNumber ? 'confirmed' : 'pending',
              timestamp: new Date(),
              gasPrice: parseInt(txResponse.data.transaction.gasPrice, 16) || 0,
              gasLimit: parseInt(txResponse.data.transaction.gas, 16) || 0,
              gasUsed: parseInt(txResponse.data.transaction.gasUsed, 16) || 0,
              blockNumber: txResponse.data.transaction.blockNumber
                ? parseInt(txResponse.data.transaction.blockNumber, 16)
                : null,
              alertType: alertType,
              latitude: location.latitude,
              longitude: location.longitude,
            },
            ...pendingTransactions
            ]);
        } catch (txError: any) {
          console.error(
        `[AlertTx] Failed to fetch or push transaction details for hash ${response.data.hash}:`,
        txError?.response?.data || txError.message
          );
        }
      }

    } catch (error: any) {
      console.error(
        `[AlertTx] Failed to submit alert from ${validator.name || validator.address}:`,
        error?.response?.data || error.message
      );
    }

    idx++;
  }, ALERT_INTERVAL_MS);
}