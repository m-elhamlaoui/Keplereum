import React, { createContext, useContext, useState, useEffect, ReactNode } from "react";
import axios from "axios";
import {
  BlockTransaction,
  Block,
  Validator,
  NetworkStats,
  BlockchainContextType,
  AlertTransaction,
  AlertValidation,
} from "@/types/blockchain";

// _______________________ //
// * Normal Transactions * //
// _______________________ //
import { startNormalTransactions } from "@/utils/transactions/normalTransactionsSubmitter"; 

// _______________________ //
// * Alert Transactions * //
// _______________________ //
import { startAlertTransactions } from "@/utils/transactions/alertTransactionsSubmitter";

// ________________________________________ //
// * Confirmations / Actions Transactions * //
// ________________________________________ //
import { startConfirmationOrAction } from "@/utils/transactions/confirmationOrActionSubmitter";

// ------------------ //
// JWT Token & axios instance with auth header
const JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3NDgyMjU2MTYsImV4cCI6MTc0ODMxMjAxNn0.0P1E-MppPM_Tn2MpsbrIBZdfWE-aEoay1FX9jWPAlIw";

const axiosInstance = axios.create({
  headers: {
    Authorization: `Bearer ${JWT_TOKEN}`,
  },
});

const BlockchainContext = createContext<BlockchainContextType | undefined>(undefined);

const parseBlock = (data: any): Block => {
  return {
    number: data.number,
    hash: data.hash,
    parentHash: data.parentHash,
    sha3uncles: data.sha3uncles,
    transactionRoot: data.transactionRoot,
    timestamp: new Date(data.timestamp),
    validator: data.validator,
    size: data.size,
    gasUsed: data.gasUsed,
    gasLimit: data.gasLimit,
    transactions: data.transactions || [], 
    transactionCount: data.transactionCount,
    totalFees: data.totalFees
  };
};

const placeholderNetworkStats: NetworkStats = {
  avgBlockTime: 0,
  pendingTransactions: 0,
  activeValidators: 0,
  totalBlocks: 0,
  difficulty: 0,
  difficultyPercent: 0,
  hashRate: 0,
  hashRateChange: 0,
  latency: 0,
  latencyChange: 0,
  gasPrice: {
    low: 0,
    medium: 0,
    high: 0,
  },
  memoryUsage: {
    total: 0,
  },
};

export const BlockchainProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [blocks, setBlocks] = useState<Block[]>([]);
  const [pendingTransactions, setPendingTransactions] = useState<BlockTransaction[]>([]);
  const [validators, setValidators] = useState<Validator[]>([]);
  const [networkStats, setNetworkStats] = useState<NetworkStats>(placeholderNetworkStats);
  const [loading, setLoading] = useState(true);
  const [selectedBlock, setSelectedBlock] = useState<Block | null>(null);
  const [alerts, setAlerts] = useState<AlertTransaction[]>([]);
  const [validations, setValidations] = useState<AlertValidation[]>([]);
  const [validatorsReady, setValidatorsReady] = useState(false);

  //------------------------------//
  //-- I - Fetch all validators --//
  //------------------------------//
  const fetchValidators = async () => {
    try {
      const response = await axiosInstance.get("http://localhost:8222/api/v1/blockchain/validators/queue");
      
      // The response.data is an array of addresses (strings)
      const validatorAddresses: string[] = response.data;

      // Map addresses to validator objects with default values
      const mappedValidators = validatorAddresses.map((address, index) => ({
        address,
        name: `Validator-${index + 1}`,  // Example default name
        blocksValidated: 0,               // Default 0 or fetch if you have more data
        isActive: true,                   // Assume active, change if needed
      }));

      setValidators(mappedValidators);
      setValidatorsReady(true);
    } catch (error) {
      console.error("Error fetching validators:", error);
      setValidatorsReady(false);
    }
  };


  // --> Validators Mounting <-- //
  useEffect(() => {
    fetchValidators();
  }, []);

  //--------------------------------//
  //-- II - Fetch all validations --//
  //--------------------------------//
  const fetchValidations = async (): Promise<AlertValidation[]> => {
    try {
      const response = await axiosInstance.get("http://localhost:8222/api/v1/blockchain/validations");
      return response.data as AlertValidation[];
    } catch (error) {
      console.error("Error fetching validations:", error);
      return [];
    }
  };

  //----------------------------//
  //-- III - Fetch all alerts --//
  //-- III - Fetch alerts without Validations --//
  //----------------------------//
  const fetchAlertsWithoutValidations = async (validations: AlertValidation[]): Promise<AlertTransaction[]> => {
    try {
      const response = await axiosInstance.get("http://localhost:8222/api/v1/blockchain/alerts");
      const allAlerts = response.data as AlertTransaction[];

      const validatedIds = new Set(validations.map(v => v.alertId));
      return allAlerts.filter(alert => !validatedIds.has(alert.alertId));
    } catch (error) {
      console.error("Error fetching alerts without validations:", error);
      return [];
    }
  };

  //----------------------------//
  //-- IV - Fetch pending txs --//
  //----------------------------//
  const fetchPendingTransactions = async (current_alerts: AlertTransaction[]) => {
    try {
      const response = await axiosInstance.get("http://localhost:8222/api/v1/blockchain/mempool");
      const mempoolTransactions = response.data;

      const formattedTransactions: BlockTransaction[] = mempoolTransactions.map((tx: any) => {
        const matchingAlert = current_alerts.find(alert => alert.transactionHash === tx.hash);
        const hashAsciiDecimal = tx.hash
          .split('')
          .reduce((sum: number, char: string) => sum + char.charCodeAt(0), 0)
          .toString();

        const truncateHash = (hash: string) =>
          hash.length > 16 ? `${hash.slice(0, 14)}...` : hash;

        return {
          hash: truncateHash(tx.hash),
          hashAsciiDecimal,
          from: tx.from,
          to: tx.to,
          value: tx.value,
          gas: tx.gas,
          gasPrice: tx.gasPrice,
          nonce: tx.nonce,
          blockNumber: null,
          alertType: matchingAlert?.alertType,
          latitude: matchingAlert?.latitude,
          longitude: matchingAlert?.longitude,
        };
      });

      setPendingTransactions(formattedTransactions);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching pending transactions:", error);
      setLoading(false); 
    }
  };

  //------------------------------------------------//
  //-- V - Fetch all data (for a Global Callback) --//
  //------------------------------------------------//
  const fetchAllData = async () => {
    try {
      setAlerts([]);
      setValidations([]);

      const validations: AlertValidation[] = await fetchValidations();
      const alerts: AlertTransaction[] = await fetchAlertsWithoutValidations(validations);

      setValidations(validations);
      setAlerts(alerts);
      console.log("Recently Fetched alerts:", alerts);

      await fetchPendingTransactions(alerts);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // --> Data Mounting <-- //
  useEffect(() => {
    fetchAllData();
  }, []);

  //-- Network Stats Parsing --//
  const parseNetworkStats = (metrics: any): NetworkStats => {
    return {
      avgBlockTime: parseFloat((metrics.averageBlockTime || 0).toFixed(3)),
      pendingTransactions: metrics.pendingTransactions || 0,
      activeValidators: metrics.activeValidators || 0,
      totalBlocks: blocks.length,
      difficulty: parseFloat((parseInt(metrics.difficulty, 10) || 0).toFixed(3)),
      difficultyPercent: parseFloat(
        Math.min(100, (parseInt(metrics.difficulty, 10) / 150000) * 100).toFixed(3)
      ),
      hashRate: parseFloat((parseInt(metrics.hashrate, 16) || 0).toFixed(3)),
      hashRateChange: parseFloat((0).toFixed(3)),
      latency: parseFloat((metrics.latency || 0).toFixed(3)),
      latencyChange: parseFloat((0).toFixed(3)),
      gasPrice: {
        low: parseFloat((metrics.gasPrice?.low || 0).toFixed(3)),
        medium: parseFloat((metrics.gasPrice?.medium || 0).toFixed(3)),
        high: parseFloat((metrics.gasPrice?.high || 0).toFixed(3)),
      },
      memoryUsage: {
        total: metrics.memoryUsage,
      },
    };
  };

  /** WebSocket connection to fetch latest
    * blocks and subscribe to new blocks
    ***/
  useEffect(() => {
    const socket = new WebSocket("ws://localhost:8080/ws");

    //-- ON OPEN --//
    socket.onopen = () => {
      console.log("WebSocket connected");
      //-- 1) 6 latest blocks fetching --//
      socket.send(
        JSON.stringify({
          type: "latestblocks",
          payload: { count: 6 },
        })
      );
      //-- 2) Toggle Mining --//
      socket.send(
        JSON.stringify({
          type: 'togglemining',
          payload: {
              start: true
          }
        })
      );
      //-- 3) Subscribe to new blocks --//
      socket.send(
        JSON.stringify({
          type: "subscribe",
        })
      );
    };

    //-- ON MESSAGE --//
    socket.onmessage = (event) => {
      try {
        const message = JSON.parse(event.data);
        console.log("Message received:", message);

        switch (message.type) {
          case "latestBlocks":
            setBlocks(message.data.map(parseBlock));
            setNetworkStats(parseNetworkStats(message.metrics));
            setLoading(false);
            break;

          case "newBlock":
            console.log("New block received:", message.data);
            setBlocks((prevBlocks) => [parseBlock(message.data), ...prevBlocks.slice(0, 9)]);
            //-- Fetch all data again --//
            fetchAllData();
            break;

          default:
            console.log("Unknown message type:", message.type);
        }
      } catch (err) {
        console.error("Error processing message:", err);
      }
    };

    //-- ON CLOSE --//
    socket.onclose = () => {
      console.log("WebSocket disconnected");
    };

    //-- ON ERROR --//
    socket.onerror = (err) => {
      console.error("WebSocket error:", err);
    };

    return () => {
      socket.send(
        JSON.stringify({
          type: 'togglemining',
          payload: {
              start: false
          }
        })
      );
      socket.close();
    };
  }, []);

  /**
   * Initialize NORMAL transactions with reset callback
   */
  useEffect(() => {
    let intervalId: NodeJS.Timeout | null = null;
    const initializeNormalTransactions = async () => {
      try {
        if (validatorsReady) {
          startNormalTransactions(validators, fetchAllData);
        }
      } catch (error) {
        console.error("Error initializing normal transactions:", error);
      }
    };
    initializeNormalTransactions();
    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [validatorsReady]);

  /**
   * Initialize ALERT transactions with reset callback
   */
  useEffect(() => {
    if (!alerts.length || !validatorsReady) return;

    const initializeAlertTransactions = async () => {
      try {
        startAlertTransactions(validators, alerts, setPendingTransactions, pendingTransactions);
      } catch (error) {
        console.error("Error initializing alert transactions:", error);
      }
    };

    initializeAlertTransactions();
    
  }, [alerts, validatorsReady]);

  const value = {
    blocks,
    latestBlock: blocks.length > 0 ? blocks[0] : null,
    pendingTransactions,
    validators,
    networkStats,
    loading,
    selectedBlock,
    setSelectedBlock,
  };

  return (
    <BlockchainContext.Provider value={value}>
      {children}
    </BlockchainContext.Provider>
  );
};

export const useBlockchainContext = () => {
  const context = useContext(BlockchainContext);
  if (context === undefined) {
    throw new Error('useBlockchainContext must be used within a BlockchainProvider');
  }
  return context;
};
