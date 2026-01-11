
import React from 'react';
import BlockchainWindow from './BlockchainWindow';
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";

const MemoryVisualization = () => {
  // Mock function to generate the memory visualization grid
  const generateGrid = (type: string) => {
    const colors = {
      low: 'bg-green-800',
      medium: 'bg-green-600',
      high: 'bg-green-400',
      highlight: 'bg-blue-500'
    };
    
    const grid = [];
    const cellCount = 400; // 20x20 grid
    
    for (let i = 0; i < cellCount; i++) {
      // Generate varying sizes for cells to mimic the mempool visualization
      const size = Math.random();
      let cellSize = 'w-2 h-2';
      let color = colors.low;
      
      if (size > 0.99) {
        cellSize = 'w-16 h-16';
        color = colors.highlight;
      } else if (size > 0.95) {
        cellSize = 'w-8 h-8';
        color = colors.high;
      } else if (size > 0.85) {
        cellSize = 'w-4 h-4';
        color = colors.medium;
      }
      
      grid.push(
        <div 
          key={i} 
          className={`${cellSize} ${color} rounded m-[1px] opacity-90 hover:opacity-100 transition-opacity`}
        />
      );
    }
    
    return grid;
  };
  
  return (
    <BlockchainWindow title="MEMPOOL VISUALIZATION" className="h-full">
      <div className="p-2 h-full">
        <Tabs defaultValue="all" className="h-full">
          <TabsList className="bg-satellite-dark w-full justify-start rounded-none border-b border-satellite-border">
            <TabsTrigger value="all" className="text-xs">All</TabsTrigger>
            <TabsTrigger value="consolidation" className="text-xs">Consolidation</TabsTrigger>
            <TabsTrigger value="coinjoins" className="text-xs">Coinjoins</TabsTrigger>
            <TabsTrigger value="data" className="text-xs">Data</TabsTrigger>
          </TabsList>
          
          <TabsContent value="all" className="h-[calc(100%-42px)] flex items-center justify-center">
            <div className="w-full h-full flex flex-wrap content-start justify-center p-2">
              {generateGrid('all')}
            </div>
          </TabsContent>
          
          <TabsContent value="consolidation" className="h-[calc(100%-42px)] flex items-center justify-center">
            <div className="w-full h-full flex flex-wrap content-start justify-center p-2">
              {generateGrid('consolidation')}
            </div>
          </TabsContent>
          
          <TabsContent value="coinjoins" className="h-[calc(100%-42px)] flex items-center justify-center">
            <div className="w-full h-full flex flex-wrap content-start justify-center p-2">
              {generateGrid('coinjoins')}
            </div>
          </TabsContent>
          
          <TabsContent value="data" className="h-[calc(100%-42px)] flex items-center justify-center">
            <div className="w-full h-full flex flex-wrap content-start justify-center p-2">
              {generateGrid('data')}
            </div>
          </TabsContent>
        </Tabs>
      </div>
    </BlockchainWindow>
  );
};

export default MemoryVisualization;
