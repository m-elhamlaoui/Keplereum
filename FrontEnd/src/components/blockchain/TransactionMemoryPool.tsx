import React from 'react';
import { useBlockchainContext } from '@/contexts/BlockchainContext';
import BlockchainWindow from './BlockchainWindow';
import { Clock, ArrowRight, AlertTriangle, Check, Satellite } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { ScrollArea } from '@/components/ui/scroll-area';
import { formatDistanceToNow } from 'date-fns';

const TransactionMemoryPool = () => {
  const { pendingTransactions } = useBlockchainContext();
  const navigate = useNavigate();
  
  const handleTransactionClick = (txId: string) => {
    navigate(`/blockchain/transaction/${txId}`);
  };

  const getTransactionType = (tx: any) => {
    if (tx.alertType) return 'ALERT_SUBMISSION';
    if (tx.confirmsAlertId) return 'ALERT_CONFIRMATION';
    if (tx.action) return 'VALIDATOR_ACTION';
    return 'STANDARD_TX';
  };

  const getTypeBadge = (type: string) => {
    const baseClass = 'text-sm px-2 py-1 rounded-full flex items-center';
    switch (type) {
      case 'ALERT_SUBMISSION':
        return (
          <span className={`${baseClass} bg-orange-900/50 text-orange-300`}>
            <AlertTriangle className="h-3 w-3 mr-1" />
            Alert
          </span>
        );
      case 'ALERT_CONFIRMATION':
        return (
          <span className={`${baseClass} bg-blue-900/50 text-blue-300`}>
            <Check className="h-3 w-3 mr-1" />
            Confirmation
          </span>
        );
      case 'VALIDATOR_ACTION':
        return (
          <span className={`${baseClass} bg-purple-900/50 text-purple-300`}>
            <Satellite className="h-3 w-3 mr-1" />
            Action
          </span>
        );
      default:
        return (
          <span className={`${baseClass} bg-gray-700/50 text-gray-300`}>
            Standard
          </span>
        );
    }
  };

  const truncateAddress = (address: string) => {
    return `${address.slice(0, 6)}...${address.slice(-4)}`;
  };

  return (
    <BlockchainWindow title="TRANSACTION MEMORY POOL" className="h-full">
      <div className="p-2 h-full flex flex-col">
        <div className="flex justify-between items-center mb-2">
          <div className="text-xs text-gray-400">Recent Transactions</div>
          <div className="text-xs text-white px-2 py-1 bg-satellite-dark-header rounded">
            {pendingTransactions.length} unconfirmed
          </div>
        </div>
        
        <ScrollArea className="flex-1">
          <div className="space-y-2 pr-2">
            {pendingTransactions.slice(0, 20).map((tx) => {
              const txType = getTransactionType(tx);
              const timeAgo = formatDistanceToNow(tx.timestamp, { addSuffix: true });
              
              return (
                <div 
                  key={tx.id} 
                  className="group bg-satellite-dark border border-satellite-border rounded p-3 hover:bg-satellite-dark/80 transition-colors cursor-pointer"
                  onClick={() => handleTransactionClick(tx.id)}
                >
                  <div className="flex justify-between items-center mb-2">
                    <div className="text-base font-extrabold font-mono text-satellite-accent">
                      {tx.hash}
                    </div>
                    <div className="flex items-center gap-4">
                      <div className="flex items-center text-sm text-white/70">
                        <Clock className="h-3 w-3 mr-1" />
                        {timeAgo}
                      </div>
                      {getTypeBadge(txType)}
                    </div>
                  </div>
                  
                  <div className="flex items-center text-xs mb-2">
                    <div className="text-white/70 w-24">
                      {tx.from}
                    </div>
                    <ArrowRight className="h-3 w-3 mx-2 text-satellite-accent flex-shrink-0" />
                    <div className="text-white/70 w-24">
                      {tx.to}
                    </div>
                    <div className="ml-auto text-green-500 font-medium">
                      {tx.amount?.toFixed(4) || '0'} SAT
                    </div>
                  </div>

                  {/* Alert-specific details */}
                  {txType === 'ALERT_SUBMISSION' && (
                    <div className="text-xs text-white/70 mt-1">
                      <div className="flex gap-2">
                        <span>Type: <span className="text-orange-300">{tx.alertType}</span></span>
                        <span>Coordinates: <span className="text-satellite-accent">{tx.latitude?.toFixed(2)}, {tx.longitude?.toFixed(2)}</span></span>
                      </div>
                    </div>
                  )}

                  {txType === 'ALERT_CONFIRMATION' && (
                    <div className="text-xs text-white/70 mt-1">
                      Confirms: <span className="text-blue-300 font-mono">{tx.confirmsAlertId}</span>
                    </div>
                  )}

                  {txType === 'VALIDATOR_ACTION' && (
                    <div className="text-xs text-white/70 mt-1">
                      Action: <span className="text-purple-300">{tx.action}</span>
                    </div>
                  )}

                  <div className="flex justify-between items-center mt-2 text-xs text-white/50">
                    <div>
                      Fee: <span className="text-satellite-accent">{tx.fee?.toFixed(5) || '0'} SAT</span>
                    </div>
                    <div>
                      Gas: {tx.gasLimit} | Price: {tx.gasPrice} Gwei
                    </div>
                  </div>
                </div>
              );
            })}
            
            {pendingTransactions.length === 0 && (
              <div className="text-center text-white/50 text-sm py-4">
                No pending transactions in memory pool
              </div>
            )}
          </div>
        </ScrollArea>
      </div>
    </BlockchainWindow>
  );
};

export default TransactionMemoryPool;