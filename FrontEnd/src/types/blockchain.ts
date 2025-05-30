//** Transaction Interface **//
export interface BlockTransaction {
  id: string;
  hash: string;
  from: string;
  to: string;
  amount: number;
  fee: number;
  status: 'confirmed' | 'pending';
  timestamp: Date;
  gasPrice: number;
  gasLimit: number;
  gasUsed: number;
  blockNumber: number | null;
  alertType?: string;
  latitude?: number;
  longitude?: number;
  confirmsAlertId?: string;
  action?: 'SWITCH_ORBIT' | 'SWITCH_SENSOR';
}

//** 1- Alert Transaction Interface **//
export interface AlertTransaction {
  longitude: number;
  alertId: string;
  alertType: string;
  latitude: number;
  transactionHash: string;
    confirmations: {
    publicAddress: string;
    confirmationHash: string;
  }[];
}

//** 2- Alert Validation Interface **//
export interface AlertValidation {
  validatorAddress: string;
  alertId: string;
  actionType: string;
  toAddress: string;
}

//* Block Interface *//
export interface Block {
  number: number;
  hash: string;
  parentHash: string;
  sha3uncles: string;
  transactionRoot: string;
  timestamp: Date;
  validator: string;
  size: number;
  gasUsed: number;
  gasLimit: number;
  transactions: BlockTransaction[];
  transactionCount: number;
  totalFees: number;
}

/** Validator Interface **/
export interface Validator {
  address: string;
  privateKey: string;
  name: string;
  blocksValidated: number;
  isActive: boolean;
}

/** Network Stats Interface **/
export interface NetworkStats {
  avgBlockTime: number;
  pendingTransactions: number;
  activeValidators: number;
  totalBlocks: number;
  difficulty: number;
  difficultyPercent: number;
  hashRate: number;
  hashRateChange: number;
  latency: number;
  latencyChange: number;
  memoryUsage: {
    total: number;
  };
  gasPrice: {
    low: number;
    medium: number;
    high: number;
  };
}

/** Context (BLOCKCHAIN) Interface **/
export interface BlockchainContextType {
  blocks: Block[];
  latestBlock: Block | null;
  pendingTransactions: BlockTransaction[];
  validators: Validator[];
  networkStats: NetworkStats;
  loading: boolean;
  selectedBlock: Block | null;
  setSelectedBlock: (block: Block | null) => void;
}