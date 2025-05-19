import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { logoutUser } from '../utils/api';

interface AuthContextType {
  isAuthenticated: boolean;
  login: () => void;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  const login = () => {
    setIsAuthenticated(true);
  };

  const logout = async () => {
    try {
      await logoutUser(); // Call the backend to clear the JWT cookie
      setIsAuthenticated(false);
    } catch (error) {
      console.error('Error during logout:', error);
      // Still clear auth state even if the backend call fails
      setIsAuthenticated(false);
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
} 