import React, { createContext, useContext, useState, useEffect, ReactNode } from "react";
import axios from "axios";
import {
  BlockTransaction,
  Block,
  Validator,
  NetworkStats,
  BlockchainContextType
} from "@/types/blockchain";

// _______________________ //
// * Normal Transactions * //
// _______________________ //
import { startNormalTransactions } from "@/utils/transactions/normalTransactionsSubmitter"; 

// _______________________ //
// * Alert Transactions * //
// _______________________ //
import { startAlertTransactions } from "@/utils/transactions/alertTransactionsSubmitter";

const BlockchainContext = createContext<BlockchainContextType | undefined>(undefined);

/**
 * Temporary Function to generate Dummy / Mock Transactions
 * @param blockNumber
 * @param id
 */
// const generateMockTransaction = (blockNumber: number, id: number): BlockTransaction => {
//   const now = new Date();
//   const timestamp = new Date(now.getTime() - Math.random() * 3600000);
//   const status = Math.random() > 0.1 ? 'confirmed' : 'pending';
//   const isAlertTx = Math.random() > 0.3;

//   // Base transaction
//   const baseTx: BlockTransaction = {
//     id: `0x${id.toString(16).padStart(64, '0')}`,
//     hash: `0x${Math.random().toString(16).substring(2, 66)}`,
//     from: `0x${Math.random().toString(16).substring(2, 42)}`,
//     to: `0x${Math.random().toString(16).substring(2, 42)}`,
//     amount: parseFloat((Math.random() * 10).toFixed(4)),
//     fee: parseFloat((Math.random() * 0.1).toFixed(5)),
//     status: status,
//     timestamp: timestamp,
//     gasPrice: Math.floor(Math.random() * 100) + 10,
//     gasLimit: 21000,
//     gasUsed: status === 'confirmed' ? Math.floor(Math.random() * 21000) : 0,
//     blockNumber: status === 'confirmed' ? blockNumber : null,
//   };

//   if (!isAlertTx) return baseTx;

//   // Alert-specific data
//   const alertTypes = ['FIRE', 'CYCLONE', 'TSUNAMI'];
//   const actions: Array<BlockTransaction['action']> = ['SWITCH_ORBIT', 'SWITCH_SENSOR'];
  
//   const txType = Math.random() > 0.5 ? 'ALERT_SUBMISSION' : 
//                  Math.random() > 0.5 ? 'ALERT_CONFIRMATION' : 'VALIDATOR_ACTION';

//   switch (txType) {
//     case 'ALERT_SUBMISSION':
//       return {
//         ...baseTx,
//         to: '0xAlertContractAddress',
//         alertType: alertTypes[Math.floor(Math.random() * alertTypes.length)],
//         latitude: parseFloat((Math.random() * 180 - 90).toFixed(6)),
//         longitude: parseFloat((Math.random() * 360 - 180).toFixed(6)),
//       };

//     case 'ALERT_CONFIRMATION':
//       return {
//         ...baseTx,
//         to: '0xAlertContractAddress',
//         confirmsAlertId: `0x${Math.random().toString(16).substring(2, 66)}`,
//       };

//     case 'VALIDATOR_ACTION':
//       return {
//         ...baseTx,
//         from: '0xValidatorAddress',
//         to: '0xActionContractAddress',
//         action: actions[Math.floor(Math.random() * actions.length)],
//       };

//     default:
//       return baseTx;
//   }
// };

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
  const [alerts, setAlerts] = useState<BlockTransaction[]>([]);
  const [validatorsReady, setValidatorsReady] = useState(false); // Track readiness

  const fetchValidators = async () => {
    try {
      const response_blockchain = await axios.get("http://localhost:8222/api/v1/blockchain/validators/queue");
      const response_back = await axios.get("http://localhost:8222/api/v1/nodes/authorities");

      const blockchainValidators = response_blockchain.data;
      const authorityNodes = response_back.data;

      // Map each satellite with its corresponding public address
      const mappedValidators: Validator[] = authorityNodes.map((node: any, index: number) => {
        const matchingValidator = blockchainValidators.find(
          (validator: any) => validator.publicAddress === node.publicKey
        );

        return {
          address: node.publicKey,
          privateKey: node.privateKey,
          name: node.nodeName || `Satellite-${index + 1}`,
          blocksValidated: matchingValidator?.blocksValidated || node.blocksValidated || 0,
          isActive: matchingValidator?.isActive || node.authorityStatus || false,
        };
      });

      setValidators(mappedValidators);
      setValidatorsReady(true);

      //-- Start normal transactions after validators are set --//
      startNormalTransactions(mappedValidators);
    } catch (error) {
      console.error("Error fetching validators:", error);
    }
  };

  const fetchAlerts = async () => {
    try {
      const alerts_response = await axios.get("http://localhost:8222/api/v1/blockchain/contract/alerts");
      const alertsData = alerts_response.data;

      //-- Fetch full transaction info for each alert by hash --//
      const alertTransactions = await Promise.all(
        alertsData.map(async (alert: any) => {
          try {
            const txResponse = await axios.get("http://localhost:8222/api/v1/blockchain/transaction", {
              params: { hash: alert.hash },
            });
            return { ...txResponse.data, ...alert };
          } catch (err) {
            console.error(`Failed to fetch transaction for alert hash ${alert.hash}:`, err);
            return null;
          }
        })
      );

      setAlerts(alertTransactions.filter(Boolean));
      //-- Start alert transactions after alerts are set --//
      startAlertTransactions(validators, alertTransactions);
    } catch (error) {
      console.error("Failed to fetch alerts:", error);
    }
  };

  useEffect(() => {
    fetchValidators();
  }, []);

  //-- Trigger fetchAlerts only when validators are ready --//
  useEffect(() => {
    if (validatorsReady) {
      fetchAlerts();
    }
  }, [validatorsReady]);

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

    socket.onopen = () => {
      console.log("WebSocket connected");

      //-- 6 latest blocks fetching --//
      socket.send(
        JSON.stringify({
          type: "latestblocks",
          payload: { count: 6 },
        })
      );

      socket.send(
        JSON.stringify({
          type: 'togglemining',
          payload: {
              start: true
          }
        })
      );

      //-- Subscribe to new blocks --//
      socket.send(
        JSON.stringify({
          type: "subscribe",
        })
      );
    };

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
            break;
          default:
            console.log("Unknown message type:", message.type);
        }
      } catch (err) {
        console.error("Error processing message:", err);
      }
    };

    socket.onclose = () => {
      socket.send(
        JSON.stringify({
          type: 'togglemining',
          payload: {
              start: false
          }
        })
      );

      console.log("WebSocket disconnected");
    };

    socket.onerror = (err) => {
      console.error("WebSocket error:", err);
    };

    return () => {
      socket.close();
    };
  }, []);
  
  // useEffect(() => {
  //   const mockPendingTxs = Array.from({ length: 50 }, (_, i) => 
  //     generateMockTransaction(0, i)
  //   ).filter(tx => tx.status === 'pending');
    
  //   setPendingTransactions(mockPendingTxs);
  //   setLoading(false);
  // }, []);

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
