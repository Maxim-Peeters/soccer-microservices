import { useState } from "react";
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { useAuth } from "../../contexts/AuthContext";
export const GoogleAuthButtonComponent = () => {
  const [error, setError] = useState<string | null>(null);
  const { setToken } = useAuth();

  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_AUTH_CLIENTID}>
      <div>
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            if (credentialResponse.credential) {
              setToken(credentialResponse.credential);
              setError(null);
            }
          }}
          onError={() => {
            setError("Login Failed");
          }}
        />
        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>
    </GoogleOAuthProvider>
  );
};
