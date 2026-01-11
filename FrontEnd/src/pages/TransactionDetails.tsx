import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { 
  X, FileText, ArrowLeftRight, Clock, 
  CheckCircle, AlertCircle, Hash, Calendar, 
  Wallet, HardDrive, Zap
} from 'lucide-react';
import { useBlockchainContext } from '@/contexts/BlockchainContext';
import { BlockTransaction } from '@/types/blockchain';
import BlocksHeader from '@/components/blockchain/BlocksHeader';
import { formatDistanceToNow } from 'date-fns';

const TransactionDetails = () => {
  const { transactionId } = useParams<{ transactionId: string }>();
  const { blocks, pendingTransactions } = useBlockchainContext();
  const navigate = useNavigate();
  const [transaction, setTransaction] = useState<BlockTransaction | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  
  useEffect(() => {
    if (!transactionId) return;
    
    setIsLoading(true);
    
    // Find transaction in blocks or pending transactions
    let foundTransaction: BlockTransaction | null = null;
    
    // First check in pending transactions
    const pendingTx = pendingTransactions.find(tx => tx.id === transactionId);
    if (pendingTx) {
      foundTransaction = pendingTx;
    } else {
      // Then check in blocks
      for (const block of blocks) {
        const blockTx = block.transactions.find(tx => tx.id === transactionId);
        if (blockTx) {
          foundTransaction = blockTx;
          break;
        }
      }
    }
    
    if (foundTransaction) {
      setTransaction(foundTransaction);
    } else if (blocks.length > 0 || pendingTransactions.length > 0) {
      // Only navigate away if we've loaded data and still can't find the transaction
      navigate('/blockchain', { replace: true });
    }
    
    setIsLoading(false);
  }, [transactionId, blocks, pendingTransactions, navigate]);
  
  const handleClose = () => {
    navigate('/blockchain');
  };
  
  if (isLoading) {
    return (
      <div className="flex flex-col h-screen bg-satellite-dark">
        <div className="sticky top-0 z-10">
          <BlocksHeader />
        </div>
        <div className="flex items-center justify-center flex-1 py-8">
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-satellite-accent mx-auto mb-4"></div>
            <h1 className="text-white text-xl">Loading Transaction Data...</h1>
          </div>
        </div>
      </div>
    );
  }
  
  if (!transaction) {
    return (
      <div className="flex flex-col h-screen bg-satellite-dark">
        <div className="sticky top-0 z-10">
          <BlocksHeader />
        </div>
        <div className="flex items-center justify-center flex-1 py-8">
          <div className="text-center">
            <AlertCircle className="h-12 w-12 text-red-500 mx-auto mb-4" />
            <h1 className="text-white text-xl mb-2">Transaction Not Found</h1>
            <p className="text-gray-400">The requested transaction could not be found in the blockchain or memory pool.</p>
            <button
              onClick={handleClose}
              className="mt-4 px-4 py-2 bg-satellite-accent hover:bg-satellite-accent/80 rounded text-white"
            >
              Back to Blockchain
            </button>
          </div>
        </div>
      </div>
    );
  }
  
  const timeAgo = formatDistanceToNow(transaction.timestamp, { addSuffix: true });
  const containingBlock = transaction.status === 'confirmed' 
    ? blocks.find(block => block.transactions.some(tx => tx.id === transactionId))
    : null;
  
  // Enhanced transaction type detection (matching your memory pool component)
  const getTransactionType = () => {
    if (transaction.alertType) return 'ALERT_SUBMISSION';
    if (transaction.confirmsAlertId) return 'ALERT_CONFIRMATION';
    if (transaction.action) return 'VALIDATOR_ACTION';
    return 'STANDARD_TX';
  };
  
  const transactionType = getTransactionType();
  
  const getTypeBadge = () => {
    const baseClass = 'text-base font-bold px-4 py-1 rounded-full flex items-center';
    switch (transactionType) {
      case 'ALERT_SUBMISSION':
        return (
          <span className={`${baseClass} bg-orange-900/50 text-orange-300`}>
            <AlertCircle className="h-3 w-3 mr-1" />
            Alert Submission
          </span>
        );
      case 'ALERT_CONFIRMATION':
        return (
          <span className={`${baseClass} bg-blue-900/50 text-blue-300`}>
            <CheckCircle className="h-3 w-3 mr-1" />
            Alert Confirmation
          </span>
        );
      case 'VALIDATOR_ACTION':
        return (
          <span className={`${baseClass} bg-purple-900/50 text-purple-300`}>
            <HardDrive className="h-3 w-3 mr-1" />
            Validator Action
          </span>
        );
      default:
        return (
          <span className={`${baseClass} bg-gray-700/50 text-gray-300`}>
            Standard Transaction
          </span>
        );
    }
  };
  
  return (
    <div className="flex flex-col h-full bg-satellite-dark">
      {/* Sticky Header */}
      <div className="sticky top-0 z-10">
        <BlocksHeader />
      </div>
      
      {/* Scrollable Content Area */}
      <div className="flex-1 overflow-y-auto custom-scrollbar">
        <main className="p-4 md:p-6 lg:p-8 pb-20">
          <div className="max-w-[90%] mx-auto">
            {/* Header Section */}
            <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-6">
              <div className="flex items-center">
                <FileText className="text-satellite-accent mr-3" size={28} />
                <div>
                  <h1 className="text-2xl font-bold text-white">Transaction Details</h1>
                  <div className="flex items-center gap-2 mt-1">
                    <span className="text-gray-400 text-sm font-mono break-all">
                      {transaction.hash}
                    </span>
                    <button 
                      onClick={() => navigator.clipboard.writeText(transaction.hash)}
                      className="text-gray-500 hover:text-satellite-accent"
                      title="Copy to clipboard"
                    >
                      <FileText size={14} />
                    </button>
                  </div>
                </div>
              </div>
              
              <div className="flex items-center gap-3">
                {getTypeBadge()}
                <button 
                  onClick={handleClose}
                  className="p-2 rounded-full hover:bg-satellite-dark-header text-gray-400 hover:text-white transition-colors"
                  aria-label="Close"
                >
                  <X size={20} />
                </button>
              </div>
            </div>
            
            {/* Status Card */}
            <div className="bg-satellite-dark-header rounded-lg p-4 mb-6 border border-satellite-border">
              <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
                <div className="flex items-center">
                  {transaction.status === 'confirmed' ? (
                    <>
                      <CheckCircle className="text-green-500 mr-3" size={24} />
                      <div>
                        <h3 className="text-white font-medium">Confirmed Transaction</h3>
                        <p className="text-gray-400 text-sm">
                          Included in Block #{containingBlock?.number} • {timeAgo}
                        </p>
                      </div>
                    </>
                  ) : (
                    <>
                      <Clock className="text-yellow-500 mr-3" size={24} />
                      <div>
                        <h3 className="text-white font-medium">Pending Transaction</h3>
                        <p className="text-gray-400 text-sm">
                          Waiting to be included in a block • {timeAgo}
                        </p>
                      </div>
                    </>
                  )}
                </div>
                
                {transaction.status === 'confirmed' && containingBlock && (
                  <button
                    onClick={() => navigate(`/blockchain/block/${containingBlock.number}`)}
                    className="flex items-center text-satellite-accent hover:text-satellite-accent/80 text-sm"
                  >
                    <Hash size={16} className="mr-1" />
                    View Block #{containingBlock.number}
                  </button>
                )}
              </div>
            </div>
            
            {/* Main Content Grid */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
              {/* Left Column */}
              <div className="lg:col-span-2 space-y-6">
                {/* Transaction Overview Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <FileText className="mr-2" size={18} />
                    Transaction Overview
                  </h2>
                  
                  <div className="space-y-4">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                      <div>
                        <label className="text-gray-400 text-sm">Status</label>
                        <div className="mt-1">
                          {transaction.status === 'confirmed' ? (
                            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-sm font-medium bg-green-900/40 text-green-400">
                              Confirmed
                            </span>
                          ) : (
                            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-yellow-900/40 text-yellow-400">
                              Pending
                            </span>
                          )}
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Timestamp</label>
                        <div className="mt-1 text-white text-sm flex items-center">
                          <Calendar className="mr-1" size={14} />
                          {transaction.timestamp.toLocaleString()}
                        </div>
                      </div>
                      
                      {containingBlock && (
                        <div>
                          <label className="text-gray-400 text-sm">Block</label>
                          <div className="mt-1">
                            <button
                              onClick={() => navigate(`/blockchain/block/${containingBlock.number}`)}
                              className="text-satellite-accent hover:underline text-sm flex items-center"
                            >
                              <Hash className="mr-1" size={14} />
                              {containingBlock.number}
                            </button>
                          </div>
                        </div>
                      )}
                    </div>
                    
                    {/* Transaction Type Specific Details */}
                    {transactionType === 'ALERT_SUBMISSION' && (
                      <div className="mt-4 pt-4 border-t border-satellite-border">
                        <h3 className="text-sm font-medium text-white mb-2">Alert Details</h3>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                          <div>
                            <label className="text-gray-400 text-sm">Alert Type</label>
                            <div className="mt-1 text-orange-300 text-sm">
                              {transaction.alertType}
                            </div>
                          </div>
                          <div>
                            <label className="text-gray-400 text-sm">Coordinates</label>
                            <div className="mt-1 text-satellite-accent text-sm">
                              {transaction.latitude?.toFixed(6)}, {transaction.longitude?.toFixed(6)}
                            </div>
                          </div>
                        </div>
                      </div>
                    )}
                    
                    {transactionType === 'ALERT_CONFIRMATION' && (
                      <div className="mt-4 pt-4 border-t border-satellite-border">
                        <h3 className="text-sm font-medium text-white mb-2">Confirmation Details</h3>
                        <div>
                          <label className="text-gray-400 text-sm">Confirmed Alert ID</label>
                          <div className="mt-1 text-blue-300 text-sm font-mono">
                            {transaction.confirmsAlertId}
                          </div>
                        </div>
                      </div>
                    )}
                    
                    {transactionType === 'VALIDATOR_ACTION' && (
                      <div className="mt-4 pt-4 border-t border-satellite-border">
                        <h3 className="text-sm font-medium text-white mb-2">Validator Action</h3>
                        <div>
                          <label className="text-gray-400 text-sm">Action Type</label>
                          <div className="mt-1 text-purple-300 text-sm">
                            {transaction.action}
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
                
                {/* From/To Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <ArrowLeftRight className="mr-2" size={18} />
                    Transfer Details
                  </h2>
                  
                  <div className="space-y-6">
                    <div>
                      <label className="text-gray-400 text-sm mb-2 block">From Address</label>
                      <div className="bg-satellite-dark p-3 rounded-md border border-satellite-border">
                        <div className="flex justify-between items-center">
                          <div className="text-white font-mono text-sm break-all">
                            {transaction.from}
                          </div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(transaction.from)}
                            className="text-gray-500 hover:text-satellite-accent ml-2"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>
                    </div>
                    
                    <div className="flex justify-center">
                      <div className="relative">
                        <div className="absolute inset-0 flex items-center justify-center">
                          <div className="w-full border-t border-satellite-border"></div>
                        </div>
                        <div className="relative flex justify-center">
                          <span className="bg-satellite-dark-header px-2 text-satellite-accent">
                            <ArrowLeftRight size={20} />
                          </span>
                        </div>
                      </div>
                    </div>
                    
                    <div>
                      <label className="text-gray-400 text-sm mb-2 block">To Address</label>
                      <div className="bg-satellite-dark p-3 rounded-md border border-satellite-border">
                        <div className="flex justify-between items-center">
                          <div className="text-white font-mono text-sm break-all">
                            {transaction.to}
                          </div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(transaction.to)}
                            className="text-gray-500 hover:text-satellite-accent ml-2"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              {/* Right Column */}
              <div className="space-y-6">
                {/* Value/Fee Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <Wallet className="mr-2" size={18} />
                    Value & Fees
                  </h2>
                  
                  <div className="space-y-4">
                    <div>
                      <label className="text-gray-400 text-sm">Amount</label>
                      <div className="mt-1 text-white text-xl font-mono">
                        {transaction.amount.toFixed(4)} <span className="text-sm">SAT</span>
                      </div>
                      <div className="text-green-500 text-sm mt-1">
                        ≈ ${(transaction.amount * 150).toFixed(2)} USD
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Transaction Fee</label>
                      <div className="mt-1 text-white text-lg font-mono">
                        {transaction.fee.toFixed(6)} <span className="text-sm">SAT</span>
                      </div>
                      <div className="text-green-500 text-sm mt-1">
                        ≈ ${(transaction.fee * 150).toFixed(2)} USD
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <h3 className="text-sm font-medium text-white mb-2 flex items-center">
                        <Zap className="mr-1" size={16} />
                        Gas Details
                      </h3>
                      <div className="grid grid-cols-2 gap-4">
                        <div>
                          <label className="text-gray-400 text-xs">Gas Price</label>
                          <div className="mt-1 text-white text-sm flex items-center">
                            {transaction.gasPrice} <span className="text-xs ml-1">GWEI</span>
                          </div>
                        </div>
                        <div>
                          <label className="text-gray-400 text-xs">Gas Limit</label>
                          <div className="mt-1 text-white text-sm">
                            {transaction.gasLimit}
                          </div>
                        </div>
                      </div>
                      
                      {transaction.status === 'confirmed' && transaction.gasUsed && (
                        <div className="mt-3">
                          <label className="text-gray-400 text-xs">Gas Used</label>
                          <div className="mt-1">
                            <div className="flex justify-between text-sm mb-1">
                              <span className="text-white">{transaction.gasUsed}</span>
                              <span className="text-gray-400">
                                {((transaction.gasUsed / transaction.gasLimit) * 100).toFixed(1)}%
                              </span>
                            </div>
                            <div className="w-full bg-satellite-dark rounded-full h-2">
                              <div 
                                className="bg-satellite-accent h-2 rounded-full" 
                                style={{ width: `${(transaction.gasUsed / transaction.gasLimit) * 100}%` }}
                              ></div>
                            </div>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                
                {/* Transaction Lifecycle Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <Clock className="mr-2" size={18} />
                    Transaction Lifecycle
                  </h2>
                  
                  <div className="space-y-4">
                    <div className="flex items-start">
                      <div className="flex-shrink-0 mt-1">
                        <div className="flex items-center justify-center h-6 w-6 rounded-full bg-satellite-accent/20 text-satellite-accent">
                          1
                        </div>
                      </div>
                      <div className="ml-3">
                        <p className="text-sm font-medium text-white">Transaction Created</p>
                        <p className="text-sm text-gray-400">{timeAgo}</p>
                      </div>
                    </div>
                    
                    <div className="flex items-start">
                      <div className="flex-shrink-0 mt-1">
                        <div className="flex items-center justify-center h-6 w-6 rounded-full bg-satellite-accent/20 text-satellite-accent">
                          2
                        </div>
                      </div>
                      <div className="ml-3">
                        <p className="text-sm font-medium text-white">Submitted to Memory Pool</p>
                        <p className="text-sm text-gray-400">{timeAgo}</p>
                      </div>
                    </div>
                    
                    {transaction.status === 'confirmed' ? (
                      <>
                        <div className="flex items-start">
                          <div className="flex-shrink-0 mt-1">
                            <div className="flex items-center justify-center h-6 w-6 rounded-full bg-satellite-accent/20 text-satellite-accent">
                              3
                            </div>
                          </div>
                          <div className="ml-3">
                            <p className="text-sm font-medium text-white">Included in Block</p>
                            <p className="text-sm text-gray-400">
                              Block #{containingBlock?.number} • {timeAgo}
                            </p>
                          </div>
                        </div>
                        
                        <div className="flex items-start">
                          <div className="flex-shrink-0 mt-1">
                            <div className="flex items-center justify-center h-6 w-6 rounded-full bg-green-900/40 text-green-500">
                              ✓
                            </div>
                          </div>
                          <div className="ml-3">
                            <p className="text-sm font-medium text-white">Transaction Confirmed</p>
                            <p className="text-sm text-gray-400">{timeAgo}</p>
                          </div>
                        </div>
                      </>
                    ) : (
                      <div className="flex items-start">
                        <div className="flex-shrink-0 mt-1">
                          <div className="flex items-center justify-center h-6 w-6 rounded-full bg-yellow-900/40 text-yellow-500">
                            <Clock size={14} />
                          </div>
                        </div>
                        <div className="ml-3">
                          <p className="text-sm font-medium text-white">Waiting for Block Inclusion</p>
                          <p className="text-sm text-gray-400">Pending confirmation</p>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default TransactionDetails;