import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import BlockchainDashboard from "./pages/BlockchainDashboard";
import BlockDetails from "./pages/BlockDetails";
import TransactionDetails from "./pages/TransactionDetails";
import { SatelliteProvider } from "./contexts/SatelliteContext";
import { BlockchainProvider } from "./contexts/BlockchainContext";
import Header from "./components/Header";
import { AuthProvider } from './contexts/AuthContext';
import { LoginPage } from './pages/Login';
import Index from "./pages/Index";

const queryClient = new QueryClient();

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <SatelliteProvider>
          <BlockchainProvider>
            <AuthProvider>
              <Toaster />
              <Sonner />
              <Router>
                <div className="flex flex-col h-screen">
                  <Header />
                  <div className="flex-1 overflow-hidden">
                    <Routes>
                      <Route path="/login" element={<LoginPage />} />
                      <Route path="/" element={<Index />} />
                      <Route path="/blockchain" element={<BlockchainDashboard />} />
                      <Route path="/blockchain/block/:blockNumber" element={<BlockDetails />} />
                      <Route path="/blockchain/transaction/:transactionId" element={<TransactionDetails />} />
                      <Route path="*" element={<Navigate to="/" replace />} />
                    </Routes>
                  </div>
                </div>
              </Router>
            </AuthProvider>
          </BlockchainProvider>
        </SatelliteProvider>
      </TooltipProvider>
    </QueryClientProvider>
  );
};

export default App;


