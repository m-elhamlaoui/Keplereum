import axios from "axios";

type Validator = {
  address: string;
  privateKey: string;
  name?: string;
};

const API_URL = "http://localhost:8222/api/v1/blockchain/transaction";
const GAS_PRICE = "1000000000";
const GAS_LIMIT = "21000";
const INTERVAL_MS = 25000;

// JWT token to add in headers
const JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3NDgyMjU2MTYsImV4cCI6MTc0ODMxMjAxNn0.0P1E-MppPM_Tn2MpsbrIBZdfWE-aEoay1FX9jWPAlIw";

// Create axios instance with Authorization header
const axiosInstance = axios.create({
  headers: {
    Authorization: `Bearer ${JWT_TOKEN}`,
  },
});

export function startNormalTransactions(
  validators: Validator[],
  onTransactionSubmitted: () => void
) {
  if (!validators || validators.length < 2) {
    console.warn("Need at least 2 validators to submit transactions.");
    return;
  }

  let idx = 0;
  setInterval(async () => {
    const from = validators[idx % validators.length];
    const to = validators[(idx + 1) % validators.length];

    try {
      axiosInstance.post(API_URL, null, {
        params: {
          privateKey: from.privateKey,
          toAddress: to.address,
          value: "0",
          gasPrice: GAS_PRICE,
          gasLimit: GAS_LIMIT,
        },
      }).then(response => {
        console.log(
          `[AutoTx] Sent from ${from.name || from.address} to ${to.name || to.address}:`,
          response.data
        );
      }).catch(error => {
        console.error(
          `[AutoTx] Failed from ${from.name || from.address} to ${to.name || to.address}:`,
          error?.response?.data || error.message
        );
      });

      //-- Call the callback to reset transactions --//
      onTransactionSubmitted();

    } catch (error: any) {
      console.error(
        `[AutoTx] Failed from ${from.name || from.address} to ${to.name || to.address}:`,
        error?.response?.data || error.message
      );
    }

    idx++;
  }, INTERVAL_MS);
}
