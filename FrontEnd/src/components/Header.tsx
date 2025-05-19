import { useState, useEffect } from "react";
import { useSatelliteContext } from "@/contexts/SatelliteContext";
import { Database, Satellite, LogOut } from "lucide-react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Header = () => {
  const { selectedSatellite } = useSatelliteContext();
  const { logout } = useAuth();
  const [currentTime, setCurrentTime] = useState(new Date());
  const [missionElapsedTime, setMissionElapsedTime] = useState(0);
  const navigate = useNavigate();
  const location = useLocation();
  
  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);
    
    return () => clearInterval(intervalId);
  }, []);
  
  // Track mission elapsed time (simulated)
  useEffect(() => {
    if (!selectedSatellite) return;
    
    const startTime = Date.now() - 3600000; // Assume mission started 1 hour ago
    
    const metInterval = setInterval(() => {
      const elapsedSeconds = Math.floor((Date.now() - startTime) / 1000);
      setMissionElapsedTime(elapsedSeconds);
    }, 1000);
    
    return () => clearInterval(metInterval);
  }, [selectedSatellite]);
  
  const formattedTime = currentTime.toLocaleTimeString('en-US', {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: true
  });
  
  const formattedDate = currentTime.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });
  
  // Format mission elapsed time as HH:MM:SS
  const formatMET = (seconds: number) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };
  
  const handleLogout = async () => {
    try {
      await logout();
      navigate('/login');
    } catch (error) {
      console.error('Error during logout:', error);
      // Still navigate to login page even if there's an error
      navigate('/login');
    }
  };

  return (
    <header className="bg-satellite-dark-header py-2 px-4 border-b border-satellite-border flex justify-between items-center">
      <div className="flex items-center gap-2">
        <Satellite className="w-6 h-6 text-satellite-accent" />
        <div className="w-1 h-10 bg-satellite-accent rounded-full animate-pulse ml-1"></div>
        <h1 className="text-white text-xl font-bold pl-1">Keplereum</h1>
      </div>
      
      <div className="flex items-center gap-4">
        <div className="flex gap-2">
          <button 
            onClick={() => navigate('/')}
            className={`w-10 h-10 rounded-full flex items-center justify-center transition-colors ${
              location.pathname === '/' 
                ? 'bg-satellite-accent text-white' 
                : 'bg-satellite-dark hover:bg-satellite-accent/30 text-gray-400 hover:text-white'
            }`}
            title="Satellite Dashboard"
          >
            <Satellite className="w-5 h-5" />
          </button>
          <button 
            onClick={() => navigate('/blockchain')}
            className={`w-10 h-10 rounded-full flex items-center justify-center transition-colors ${
              location.pathname.includes('/blockchain') 
                ? 'bg-satellite-accent text-white' 
                : 'bg-satellite-dark hover:bg-satellite-accent/30 text-gray-400 hover:text-white'
            }`}
            title="Blockchain Dashboard"
          >
            <Database className="w-5 h-5" />
          </button>
          <button 
            onClick={handleLogout}
            className="w-10 h-10 rounded-full flex items-center justify-center transition-colors bg-satellite-dark hover:bg-satellite-accent/30 text-gray-400 hover:text-white"
            title="Logout"
          >
            <LogOut className="w-5 h-5" />
          </button>
        </div>
        
        <div className="w-4 h-4 bg-satellite-accent rounded-full animate-pulse"></div>
        {selectedSatellite && location.pathname === '/' && (
          <div className="flex flex-col items-end mr-2">
            <div className="px-3 py-1 bg-satellite-accent/20 rounded-full text-sm text-satellite-accent border border-satellite-accent/30">
              {selectedSatellite.name}
            </div>
            <div className="text-xs text-gray-400 mt-1">
              MET: {formatMET(missionElapsedTime)}
            </div>
          </div>
        )}
        
        <div className="text-sm text-gray-400">
          <div className="text-right">{formattedTime}</div>
          <div className="text-right text-xs opacity-80">{formattedDate}</div>
        </div>
      </div>
    </header>
  );
};

export default Header;
