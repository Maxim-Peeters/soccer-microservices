import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GoogleAuthButtonComponent } from "../components/auth/GoogleAuthButton";
import { useAuth } from "../contexts/AuthContext";

function LoginPage() {
  const { token } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      navigate("/players");
    }
  }, [token, navigate]);

  return (
    <div>
      <h1>Login</h1>
      <GoogleAuthButtonComponent />
    </div>
  );
}

export default LoginPage;

