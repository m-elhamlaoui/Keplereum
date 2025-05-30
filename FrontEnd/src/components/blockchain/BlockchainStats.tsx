import React from 'react';
import BlockchainWindow from './BlockchainWindow';
import { useBlockchainContext } from '@/contexts/BlockchainContext';

const BlockchainStats = () => {
  const { networkStats } = useBlockchainContext();
  
  return (
    <BlockchainWindow title="BLOCKCHAIN STATS" className="h-full">
      <div className="p-4 h-full flex flex-col justify-center">
        <div className="grid grid-cols-3 gap-4">
          {/* Avg Block Time */}
          <div className="text-center flex flex-col items-center justify-center">
            <div className="text-2xl text-satellite-accent font-bold">
              ~{networkStats.avgBlockTime} minutes
            </div>
            <div className="text-xs text-gray-400 mt-1">Average block time</div>
            <div className="flex items-center justify-center mt-2">
              <span className="text-red-500 text-sm font-medium">↑ 0.72 %</span>
              <span className="text-xs text-gray-500 ml-2">Previous: <span className="text-green-500">↓ 6.81 %</span></span>
            </div>
          </div>
          
          {/* Difficulty Bar */}
          <div className="h-full border-l border-r border-satellite-border flex flex-col items-center justify-center">
            <div className="text-sm font-bold text-gray-400 my-2">Network Difficulty</div>
            <div className="w-full flex flex-col items-center">
              <div className="w-5/6 bg-satellite-accent h-2 rounded-full">
                <div 
                  className="h-full rounded-full bg-gradient-to-r from-blue-500 to-green-400" 
                  style={{ width: `${Math.min(100, networkStats.difficultyPercent)}%` }}
                >
                </div>
              </div>

              <div className="text-2xl text-satellite-accent font-bold my-2">
                = {networkStats.difficulty}
              </div>
            </div>
          </div>
          
          {/* Split into Network Hash Rate and Latency */}
          <div className="flex flex-col justify-between">
            {/* Network Hash Rate */}
            <div className="text-center">
              <div className="text-2xl text-satellite-accent font-bold">
                {networkStats.hashRate} TH/s
              </div>
              <div className="text-xs text-gray-400">Network Hash Rate</div>
              <div className="text-xs text-gray-500 mt-1">
                {networkStats.hashRateChange > 0 ? '↑' : '↓'} {Math.abs(networkStats.hashRateChange)}%
              </div>
            </div>
            
            {/* Latency */}
            <div className="text-center">
              <div className="text-2xl text-satellite-accent font-bold">
                {networkStats.latency} ms
              </div>
              <div className="text-xs text-gray-400">Network Latency</div>
              <div className="text-xs text-gray-500 mt-1">
                {networkStats.latencyChange > 0 ? '↑' : '↓'} {Math.abs(networkStats.latencyChange)}%
              </div>
            </div>
          </div>
        </div>
      </div>
    </BlockchainWindow>
  );
};

export default BlockchainStats;