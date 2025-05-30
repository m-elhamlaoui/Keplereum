import axios from "axios";

type Validator = {
  address: string;
  privateKey: string;
  name?: string;
};

const CONFIRMATION_API_URL = "http://localhost:8222/api/v1/blockchain/contract/alert/confirm";
const VALIDATION_API_URL = "http://localhost:8222/api/v1/blockchain/contract/alert/action";
const CONFIRMATION_INTERVAL_MS = 5000;

const ACTION_TYPES = ["SWITCH_ORBIT", "SWITCH_SENSOR"];

function getRandomElement<T>(arr: T[]): T {
  return arr[Math.floor(Math.random() * arr.length)];
}

export function startConfirmationOrAction(
  validators: Validator[],
  alerts: any[],
  setPendingTransactions: (pendingTransactions: any[]) => void,
  pendingTransactions: any[],
) {
  if (!validators || validators.length === 0) {
    console.warn("No validators available for alert confirmations / actions.");
    return;
  }

  let idx = 0;

  setInterval(async () => {
    if (alerts.length === 0) {
      console.log("[ConfirmationOrAction] No alerts to process.");
      return;
    }

    const validator = validators[idx % validators.length];
    const alert = alerts[idx % alerts.length]; // Pick one alert at a time
    const confirmations = alert.confirmations || [];

    try {
      let response;
      const isConfirmation = confirmations.length < 3;

      let actionType;

      if (isConfirmation) {
        actionType = undefined;
        // Send a confirmation
        response = await axios.post(CONFIRMATION_API_URL, null, {
          params: {
            privateKey: validator.privateKey,
            alertId: alert.alertId,
          },
        });

        console.log(
          `[ConfirmationTx] Sent confirmation for alert (${alert.alertId}) from ${validator.name || validator.address}:`,
          response.data
        );
      } else {
        // Send a validation
        actionType = getRandomElement(ACTION_TYPES);
        const otherValidators = validators.filter(v => v.address !== validator.address);

        const toValidator = getRandomElement(otherValidators);

        response = await axios.post(VALIDATION_API_URL, null, {
          params: {
            privateKey: validator.privateKey,
            alertId: alert.alertId,
            actionType,
            toAddress: toValidator.address, 
          },
        });

        console.log(
          `[ValidationTx] Sent validation for alert (${alert.alertId}) from ${validator.name || validator.address}:`,
          response.data
        );
      }

      if (response.data) {
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
              ...(isConfirmation
                ? { confirmsAlertId: alert.alertId }
                : { action: actionType })
              },
              ...pendingTransactions
            ]);
        } catch (txError: any) {
          console.error(
            `[ConfirmationOrAction] Failed to fetch or push transaction details for hash ${response.data}:`,
            txError?.response?.data || txError.message
          );
        }
      }
    } catch (error: any) {
      console.error(
        `[ConfirmationOrAction] Failed to process alert (${alert.alertId}) from ${validator.name || validator.address}:`,
        error?.response?.data || error.message
      );
    }

    idx++;
  }, CONFIRMATION_INTERVAL_MS);
}