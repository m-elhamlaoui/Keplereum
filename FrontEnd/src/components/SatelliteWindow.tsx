
import React, { ReactNode, useState, useEffect, useRef } from "react";
import { Maximize, Minimize } from "lucide-react";

interface SatelliteWindowProps {
  title: string;
  children: ReactNode;
  className?: string;
}

const SatelliteWindow = ({ title, children, className }: SatelliteWindowProps) => {
  const [isMinimized, setIsMinimized] = useState(false);
  const [isFullscreen, setIsFullscreen] = useState(false);
  const windowRef = useRef<HTMLDivElement>(null);
  
  useEffect(() => {
    if (windowRef.current) {
      window.dispatchEvent(new Event('resize'));
    }
  }, [isFullscreen]);
  
  return (
    <div
      ref={windowRef}
      className={`dashboard-window flex flex-col ${
        isFullscreen 
          ? "fixed inset-0 z-50 animate-fade-in" 
          : `${className} transition-all duration-300`
      }`}
      style={{ height: isFullscreen ? '100vh' : '' }}
      data-fullscreen={isFullscreen ? "true" : "false"}
    >
      <div className="window-header">
        <div className="flex items-center gap-2">
          <span className={`text-xs ${isFullscreen ? 'text-base' : ''}`}>{title}</span>
        </div>
        <div className="flex items-center gap-1">
          <button
            onClick={() => setIsMinimized(!isMinimized)}
            className="p-0.5 hover:bg-satellite-accent/20 rounded"
          >
            {isMinimized ? <Maximize size={isFullscreen ? 18 : 14} /> : <Minimize size={isFullscreen ? 18 : 14} />}
          </button>
          <button
            onClick={() => setIsFullscreen(!isFullscreen)}
            className="p-0.5 hover:bg-satellite-accent/20 rounded"
            aria-label={isFullscreen ? "Exit fullscreen" : "Enter fullscreen"}
          >
            <Maximize size={isFullscreen ? 18 : 14} />
          </button>
        </div>
      </div>
      {!isMinimized && (
        <div className={`window-body flex-1 relative ${isFullscreen ? 'fullscreen' : ''}`}>
          <div className="window-content w-full h-full">{children}</div>
        </div>
      )}
    </div>
  );
};

export default SatelliteWindow;
