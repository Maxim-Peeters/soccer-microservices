import React from "react";
import { GoogleLogin } from "@react-oauth/google";
import { useAuth } from "../context/AuthContext";

const Login: React.FC = () => {
  const { login } = useAuth(); // Access the login method from the context

  const handleLoginSuccess = (response: any) => {
    console.log("Credential Response:", response);
    if (response.credential) {
      const token = response.credential;
      login(token);
    }
  };

  

  return (
    <div>
      <GoogleLogin
        onSuccess={handleLoginSuccess}
      />
    </div>
  );
};

export default Login;
