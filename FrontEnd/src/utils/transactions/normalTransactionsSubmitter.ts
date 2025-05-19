import axios from "axios";

type Validator = {
  address: string;
  privateKey: string;
  name?: string;
};

const API_URL = "http://localhost:8222/api/v1/blockchain/transaction";
const GAS_PRICE = "1000000000";
const GAS_LIMIT = "21000";
const INTERVAL_MS = 65000;

export function startNormalTransactions(validators: Validator[]) {
  if (!validators || validators.length < 2) {
    console.warn("Need at least 2 validators to submit transactions.");
    return;
  }

  let idx = 0;
  setInterval(async () => {
    const from = validators[idx % validators.length];
    const to = validators[(idx + 1) % validators.length];

    try {
      const response = await axios.post(API_URL, null, {
        params: {
          privateKey: from.privateKey,
          toAddress: to.address,
          value: "0",
          gasPrice: GAS_PRICE,
          gasLimit: GAS_LIMIT,
        },
      });
      console.log(
        `[AutoTx] Sent from ${from.name || from.address} to ${to.name || to.address}:`,
        response.data
      );
    } catch (error: any) {
      console.error(
        `[AutoTx] Failed from ${from.name || from.address} to ${to.name || to.address}:`,
        error?.response?.data || error.message
      );
    }

    idx++;
  }, INTERVAL_MS);
}

