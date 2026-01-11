
import React from "react";
import { Card } from "@/components/ui/card";

interface BlockchainWindowProps {
  title: string;
  children: React.ReactNode;
  className?: string;
}

const BlockchainWindow: React.FC<BlockchainWindowProps> = ({ 
  title, 
  children, 
  className = "" 
}) => {
  return (
    <Card className={`flex flex-col bg-satellite-dark border-satellite-border overflow-hidden ${className}`}>
      <div className="flex justify-between items-center bg-satellite-header px-3 py-2 text-xs font-medium text-satellite-text border-b border-satellite-border">
        <h3>{title}</h3>
      </div>
      <div className="flex-1 overflow-hidden">
        {children}
      </div>
    </Card>
  );
};

export default BlockchainWindow;
