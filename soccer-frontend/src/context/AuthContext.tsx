import React, { createContext, useContext, useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode';
interface AuthContextType {
  isAuthenticated: boolean;
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = Cookies.get('auth_token');
    if (storedToken) {
        const decodedToken = jwtDecode(storedToken);
        if(decodedToken?.exp){
            const isExpired = Date.now()>=decodedToken.exp*1000; //1 hour = 3600 seconds
            if(isExpired){
                logout()
            }else{
                setIsAuthenticated(true);
                setToken(storedToken);
            }
        }      
    }
  }, []);

  const login = (newToken: string) => {
    Cookies.set('auth_token', newToken, { secure: true, sameSite: 'strict' });
    setIsAuthenticated(true);
    setToken(newToken);
    console.log("jajaa", newToken);
  };

  const logout = () => {
    Cookies.remove('auth_token');
    setIsAuthenticated(false);
    setToken(null);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

