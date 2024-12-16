import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GoogleAuthButton } from "../components/auth/GoogleAuthButton";
import { useAuth } from "../contexts/AuthContext";

function Login() {
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
      <GoogleAuthButton />
    </div>
  );
}

export default Login;

