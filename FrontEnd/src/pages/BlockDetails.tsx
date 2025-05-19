import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { 
  X, FileText, Hash, Calendar, 
  ChevronRight, Clock, CheckCircle, AlertCircle,
  Wallet, HardDrive, ArrowRight, Shield, KeyRound
} from 'lucide-react';
import { useBlockchainContext } from '@/contexts/BlockchainContext';
import BlocksHeader from '@/components/blockchain/BlocksHeader';
import { formatDistanceToNow } from 'date-fns';

const BlockDetails = () => {
  const { blockNumber } = useParams<{ blockNumber: string }>();
  const { blocks } = useBlockchainContext();
  const navigate = useNavigate();
  const [block, setBlock] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  
  useEffect(() => {
    if (!blockNumber) return;
    
    setIsLoading(true);
    
    // Convert blockNumber to a number for comparison
    const blockNum = parseInt(blockNumber, 10);
    
    if (isNaN(blockNum)) {
      navigate('/blockchain', { replace: true });
      return;
    }
    
    const foundBlock = blocks.find(b => b.number === blockNum);
    
    if (foundBlock) {
      setBlock(foundBlock);
    } else if (blocks.length > 0) {
      // Only navigate away if we've loaded data and still can't find the block
      navigate('/blockchain', { replace: true });
    }
    
    setIsLoading(false);
  }, [blockNumber, blocks, navigate]);
  
  const handleClose = () => {
    navigate('/blockchain');
  };
  
  const handleTransactionClick = (txId) => {
    navigate(`/blockchain/transaction/${txId}`);
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
            <h1 className="text-white text-xl">Loading Block Data...</h1>
          </div>
        </div>
      </div>
    );
  }
  
  if (!block) {
    return (
      <div className="flex flex-col h-screen bg-satellite-dark">
        <div className="sticky top-0 z-10">
          <BlocksHeader />
        </div>
        <div className="flex items-center justify-center flex-1 py-8">
          <div className="text-center">
            <AlertCircle className="h-12 w-12 text-red-500 mx-auto mb-4" />
            <h1 className="text-white text-xl mb-2">Block Not Found</h1>
            <p className="text-gray-400">The requested block could not be found in the blockchain.</p>
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
  
  const timeAgo = formatDistanceToNow(block.timestamp, { addSuffix: true });
  const nextBlock = blocks.find(b => b.number === block.number + 1);
  const prevBlock = blocks.find(b => b.number === block.number - 1);
  
  // Generate mock data for the additional hash values requested
  const parentHash = prevBlock ? prevBlock.hash : "0x0000000000000000000000000000000000000000000000000000000000000000";
  const sha3Uncles = `0x1${block.hash}`;
  const transactionRoot = `0x2${block.hash}`;
  
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
                <Shield className="text-satellite-accent mr-3" size={28} />
                <div>
                  <h1 className="text-2xl font-bold text-white">Block #{block.number}</h1>
                  <div className="flex items-center gap-2 mt-1">
                    <span className="text-gray-400 text-sm font-mono break-all">
                      {block.hash}
                    </span>
                    <button 
                      onClick={() => navigator.clipboard.writeText(block.hash)}
                      className="text-gray-500 hover:text-satellite-accent"
                      title="Copy to clipboard"
                    >
                      <FileText size={14} />
                    </button>
                  </div>
                </div>
              </div>
              
              <div className="flex items-center gap-3">
                <span className="text-base font-bold px-4 py-1 rounded-full flex items-center bg-green-900/50 text-green-300">
                  <CheckCircle className="h-3 w-3 mr-1" />
                  Confirmed
                </span>
                <button 
                  onClick={handleClose}
                  className="p-2 rounded-full hover:bg-satellite-dark-header text-gray-400 hover:text-white transition-colors"
                  aria-label="Close"
                >
                  <X size={20} />
                </button>
              </div>
            </div>
            
            {/* Block Navigation Card */}
            <div className="bg-satellite-dark-header rounded-lg p-4 mb-6 border border-satellite-border">
              <div className="flex justify-between items-center">
                <div className="flex items-center gap-4">
                  {prevBlock ? (
                    <button 
                      onClick={() => navigate(`/blockchain/block/${prevBlock.number}`)}
                      className="flex items-center text-satellite-accent hover:text-satellite-accent/80"
                    >
                      <ChevronRight className="h-5 w-5 transform rotate-180 mr-1" />
                      <span>Previous Block</span>
                    </button>
                  ) : (
                    <span className="text-gray-500">First Block</span>
                  )}
                  
                  {nextBlock && (
                    <button 
                      onClick={() => navigate(`/blockchain/block/${nextBlock.number}`)}
                      className="flex items-center text-satellite-accent hover:text-satellite-accent/80"
                    >
                      <span>Next Block</span>
                      <ChevronRight className="h-5 w-5 ml-1" />
                    </button>
                  )}
                </div>
                
                <div className="flex items-center">
                  <Clock className="text-gray-400 mr-2" size={16} />
                  <span className="text-gray-400 text-sm">{timeAgo}</span>
                </div>
              </div>
            </div>
            
            {/* Main Content Grid */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
              {/* Left Column */}
              <div className="lg:col-span-2 space-y-6">
                {/* Block Overview Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <Shield className="mr-2" size={18} />
                    Block Overview
                  </h2>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div className="space-y-4">
                      <div>
                        <label className="text-gray-400 text-sm">Block Number</label>
                        <div className="mt-1 text-white flex items-center">
                          <Hash className="mr-1" size={14} />
                          {block.number}
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Block Hash</label>
                        <div className="mt-1 text-white text-sm font-mono break-all flex items-center">
                          <div className="flex-1">{block.hash}</div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(block.hash)}
                            className="text-gray-500 hover:text-satellite-accent ml-2 flex-shrink-0"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Parent Hash</label>
                        <div className="mt-1 text-white text-sm font-mono break-all flex items-center">
                          <div className="flex-1">{parentHash}</div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(parentHash)}
                            className="text-gray-500 hover:text-satellite-accent ml-2 flex-shrink-0"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>

                      <div>
                        <label className="text-gray-400 text-sm">Transactions</label>
                        <div className="mt-1 text-white">
                          {block.transactionCount}
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Block Size</label>
                        <div className="mt-1 text-white">
                          {block.size} MB
                        </div>
                      </div>
                    </div>
                    
                    <div className="space-y-4">
                      <div>
                        <label className="text-gray-400 text-sm">Timestamp</label>
                        <div className="mt-1 text-white text-sm flex items-center">
                          <Calendar className="mr-1" size={14} />
                          {block.timestamp.toLocaleString()} ({timeAgo})
                        </div>
                      </div>

                      <div>
                        <label className="text-gray-400 text-sm">SHA3 Uncles</label>
                        <div className="mt-1 text-white text-sm font-mono break-all flex items-center">
                          <div className="flex-1">{sha3Uncles}</div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(sha3Uncles)}
                            className="text-gray-500 hover:text-satellite-accent ml-2 flex-shrink-0"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Transaction Root</label>
                        <div className="mt-1 text-white text-sm font-mono break-all flex items-center">
                          <div className="flex-1">{transactionRoot}</div>
                          <button 
                            onClick={() => navigator.clipboard.writeText(transactionRoot)}
                            className="text-gray-500 hover:text-satellite-accent ml-2 flex-shrink-0"
                            title="Copy to clipboard"
                          >
                            <FileText size={14} />
                          </button>
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Block Weight</label>
                        <div className="mt-1 text-white">
                          {Math.floor(block.size * 2.5)} MWU
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Block Health</label>
                        <div className="mt-1">
                          <div className="flex justify-between text-sm mb-1">
                            <span className="text-white">Network Propagation</span>
                            <span className="text-green-400">
                              100%
                            </span>
                          </div>
                          <div className="w-full bg-satellite-dark rounded-full h-2">
                            <div 
                              className="bg-green-500 h-2 rounded-full" 
                              style={{ width: '100%' }}
                            ></div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              
                
                {/* Validator Info Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <HardDrive className="mr-2" size={18} />
                    Validator Information
                  </h2>
                  
                  <div className="flex items-center space-x-4">
                    <div className="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center text-white font-bold">
                      SA
                    </div>
                    <div>
                      <div className="text-white font-medium">Satellite Alpha</div>
                      <div className="text-gray-400 text-sm flex items-center">
                        <KeyRound className="mr-1" size={14} />
                        <span className="font-mono break-all">0x87sfsd789sdfsdf978sdf</span>
                        <button
                          onClick={() => navigator.clipboard.writeText("0x87sfsd789sdfsdf978sdf")}
                          className="ml-2 text-gray-500 hover:text-satellite-accent"
                          title="Copy to clipboard"
                        >
                          <FileText size={14} />
                        </button>
                      </div>
                    </div>
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                    <div className="space-y-4">
                      <div>
                        <label className="text-gray-400 text-sm">Blocks Validated</label>
                        <div className="mt-1 text-white">
                          {block.number * 0.01 > 1 ? Math.floor(block.number * 0.01) : 1}
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Validator Uptime</label>
                        <div className="mt-1 text-white">
                          99.98%
                        </div>
                      </div>
                    </div>
                    
                    <div className="space-y-4">
                      <div>
                        <label className="text-gray-400 text-sm">Reputation Score</label>
                        <div className="mt-1">
                          <div className="flex justify-between text-sm mb-1">
                            <span className="text-white">Excellence</span>
                            <span className="text-green-400">
                              985/1000
                            </span>
                          </div>
                          <div className="w-full bg-satellite-dark rounded-full h-2">
                            <div 
                              className="bg-green-500 h-2 rounded-full" 
                              style={{ width: '98.5%' }}
                            ></div>
                          </div>
                        </div>
                      </div>
                      
                      <div>
                        <label className="text-gray-400 text-sm">Last Active</label>
                        <div className="mt-1 text-white text-sm">
                          {timeAgo}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              {/* Right Column */}
              <div className="space-y-6">
                {/* Reward Info Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <Wallet className="mr-2" size={18} />
                    Block Rewards
                  </h2>
                  
                  <div className="space-y-4">
                    <div>
                      <label className="text-gray-400 text-sm">Base Reward</label>
                      <div className="mt-1 text-white text-xl font-mono">
                        2.000 <span className="text-sm">SAT</span>
                      </div>
                      <div className="text-green-500 text-sm mt-1">
                        ≈ ${(2 * 2800).toFixed(2)} USD
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Total Fees</label>
                      <div className="mt-1 text-white text-lg font-mono">
                        {block.totalFees.toFixed(3)} <span className="text-sm">SAT</span>
                      </div>
                      <div className="text-green-500 text-sm mt-1">
                        ≈ ${(block.totalFees * 2800).toFixed(2)} USD
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Total Block Reward</label>
                      <div className="mt-1 text-white text-xl font-mono">
                        {(block.totalFees + 2).toFixed(3)} <span className="text-sm">SAT</span>
                      </div>
                      <div className="text-green-500 text-sm mt-1">
                        ≈ ${((block.totalFees + 2) * 2800).toFixed(2)} USD
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Median Transaction Fee</label>
                      <div className="mt-1 text-white">
                        ~2 <span className="text-xs">sat/vB</span> <span className="text-green-500 ml-1">($0.23)</span>
                      </div>
                    </div>
                  </div>
                </div>
                
                {/* Block Statistics Card */}
                <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border">
                  <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                    <FileText className="mr-2" size={18} />
                    Block Statistics
                  </h2>
                  
                  <div className="space-y-4">
                    <div>
                      <label className="text-gray-400 text-sm">Transaction Types</label>
                      <div className="mt-2">
                        <div className="w-full bg-satellite-dark rounded-full h-2 mb-1">
                          <div className="flex">
                            <div className="bg-blue-500 h-2 rounded-l-full" style={{ width: '60%' }}></div>
                            <div className="bg-purple-500 h-2" style={{ width: '25%' }}></div>
                            <div className="bg-orange-500 h-2 rounded-r-full" style={{ width: '15%' }}></div>
                          </div>
                        </div>
                        <div className="flex justify-between text-xs text-gray-400">
                          <div className="flex items-center">
                            <div className="w-2 h-2 bg-blue-500 rounded-full mr-1"></div>
                            <span>Standard (60%)</span>
                          </div>
                          <div className="flex items-center">
                            <div className="w-2 h-2 bg-purple-500 rounded-full mr-1"></div>
                            <span>Validator (25%)</span>
                          </div>
                          <div className="flex items-center">
                            <div className="w-2 h-2 bg-orange-500 rounded-full mr-1"></div>
                            <span>Alerts (15%)</span>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Block Capacity</label>
                      <div className="mt-2">
                        <div className="flex justify-between text-sm mb-1">
                          <span className="text-white">Usage</span>
                          <span className="text-gray-400">
                            {Math.floor(block.size / 4 * 100)}%
                          </span>
                        </div>
                        <div className="w-full bg-satellite-dark rounded-full h-2">
                          <div 
                            className="bg-satellite-accent h-2 rounded-full" 
                            style={{ width: `${Math.floor(block.size / 4 * 100)}%` }}
                          ></div>
                        </div>
                      </div>
                    </div>
                    
                    <div className="pt-3 border-t border-satellite-border">
                      <label className="text-gray-400 text-sm">Block Time</label>
                      <div className="mt-1 text-white flex items-center">
                        <Clock className="mr-1" size={14} />
                        12 seconds
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            {/* Transactions List */}
            <div className="bg-satellite-dark-header rounded-lg p-5 border border-satellite-border mb-6">
              <h2 className="text-lg font-semibold text-white mb-4 flex items-center">
                <FileText className="mr-2" size={18} />
                Transactions ({block.transactionCount})
              </h2>
              
              <div className="space-y-3 max-h-96 overflow-y-auto pr-2 custom-scrollbar">
                {block.transactions.map((tx) => (
                  <div 
                    key={tx.id} 
                    className="bg-satellite-dark border border-satellite-border rounded-lg p-4 hover:bg-satellite-dark/80 transition-colors cursor-pointer"
                    onClick={() => handleTransactionClick(tx.id)}
                  >
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                      <div className="flex items-center">
                        <div className="mr-3">
                          {tx.alertType ? (
                            <div className="w-8 h-8 flex items-center justify-center rounded-full bg-orange-900/40 text-orange-500">
                              <AlertCircle size={16} />
                            </div>
                          ) : tx.confirmsAlertId ? (
                            <div className="w-8 h-8 flex items-center justify-center rounded-full bg-blue-900/40 text-blue-500">
                              <CheckCircle size={16} />
                            </div>
                          ) : tx.action ? (
                            <div className="w-8 h-8 flex items-center justify-center rounded-full bg-purple-900/40 text-purple-500">
                              <HardDrive size={16} />
                            </div>
                          ) : (
                            <div className="w-8 h-8 flex items-center justify-center rounded-full bg-gray-700/40 text-gray-300">
                              <FileText size={16} />
                            </div>
                          )}
                        </div>
                        <div>
                          <div className="text-satellite-accent font-mono text-xs md:text-sm">
                            {tx.hash}
                          </div>
                          <div className="text-gray-400 text-xs mt-1">
                            {formatDistanceToNow(tx.timestamp, { addSuffix: true })}
                          </div>
                        </div>
                      </div>
                      
                      <div className="flex items-center">
                        <div className="text-xs md:text-sm text-white/70">
                          {tx.from}
                        </div>
                        <ArrowRight className="h-3 w-3 mx-2 text-satellite-accent" />
                        <div className="text-xs md:text-sm text-white/70">
                          {tx.to}
                        </div>
                      </div>
                      
                      <div className="text-right">
                        <div className="text-green-500 text-xs md:text-sm">
                          {tx.amount.toFixed(4)} SAT
                        </div>
                        <div className="text-gray-400 text-xs mt-1">
                          Fee: {tx.fee.toFixed(5)} SAT
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
                
                {block.transactionCount > 20 && (
                  <div className="text-center py-4">
                    <button className="px-4 py-2 bg-satellite-accent/20 hover:bg-satellite-accent/30 text-satellite-accent rounded-lg transition-colors">
                      View {block.transactionCount - 20} More Transactions
                    </button>
                  </div>
                )}
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default BlockDetails;