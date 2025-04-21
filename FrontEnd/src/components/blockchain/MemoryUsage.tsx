
import BlockchainWindow from './BlockchainWindow';
import { AreaChart, Area, XAxis, Tooltip, ResponsiveContainer } from 'recharts';
import { useBlockchainContext } from '@/contexts/BlockchainContext';

const MemoryUsage = () => {
  const { pendingTransactions } = useBlockchainContext();
  
  // Generate mock data for incoming transactions chart
  const generateChartData = () => {
    const data = [];
    const timestamps = ["21:45", "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30"];
    
    for (let i = 0; i < timestamps.length; i++) {
      const baseValue = 1000 + Math.random() * 1000;
      const peakValue = Math.random() > 0.7 ? baseValue + Math.random() * 1000 : baseValue - Math.random() * 1000;
      
      data.push({
        time: timestamps[i],
        transactions: Math.floor(baseValue),
        peaks: Math.floor(peakValue),
      });
    }
    
    return data;
  };
  
  const data = generateChartData();
  
  // Calculate memory usage based on pending transactions //
  const memoryUsage = Math.floor(10.6 + (pendingTransactions.length * 0.02));
  const memoryPercentage = (memoryUsage / 300) * 100;
  
  return (
    <BlockchainWindow title="MEMORY USAGE & TRANSACTIONS" className="h-full">
        <div className="flex flex-col h-full">
          <div className="flex justify-between items-center mb-3 mt-3">
            <div className="bg-satellite-dark p-2 rounded ml-6">
              <div className="text-xs text-gray-400 mb-1">Minimum fee</div>
              <div className="text-xl text-white">1.00 <span className="text-xs">sat/vB</span></div>
            </div>

            <div className="bg-satellite-dark p-2 rounded text-center">
              <div className="text-xs text-gray-400 mb-1">Memory Usage</div>
              <div className="text-sm text-white mb-1">{memoryUsage.toFixed(1)} MB / 300 MB</div>
              <div className="w-full bg-gray-700 rounded-full h-2">
                <div 
                  className="bg-satellite-accent h-2 rounded-full transition-all duration-500" 
                  style={{ width: `${memoryPercentage}%` }}
                ></div>
              </div>
            </div>

            <div className="bg-satellite-dark p-2 rounded text-right mr-6">
              <div className="text-xs text-gray-400 mb-1">Unconfirmed</div>
              <div className="text-xl text-white">{pendingTransactions.length} <span className="text-xs">TXs</span></div>
            </div>
          </div>
          
          <div className="flex-grow w-full h-full">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart data={data} margin={{ top: 5, right: 20, left: 20, bottom: 5 }}>
              <defs>
                <linearGradient id="colorTx" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#2d7bb2" stopOpacity={0.9}/>
                  <stop offset="95%" stopColor="#2d7bb2" stopOpacity={0.1}/>
                </linearGradient>
                <linearGradient id="colorPeaks" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#3d8445" stopOpacity={0.9}/>
                  <stop offset="95%" stopColor="#3d8445" stopOpacity={0.1}/>
                </linearGradient>
              </defs>
              <XAxis dataKey="time" tick={{ fontSize: 10 }} />
              <Tooltip contentStyle={{ fontSize: 12 }} />
              <Area type="monotone" dataKey="transactions" stroke="#2d7bb2" strokeWidth={3} fillOpacity={1} fill="url(#colorTx)" />
              <Area type="monotone" dataKey="peaks" stroke="#3d8445" strokeWidth={3} fillOpacity={1} fill="url(#colorPeaks)" />
            </AreaChart>
          </ResponsiveContainer>
        </div>
        </div>
    </BlockchainWindow>
  );
};

export default MemoryUsage;
