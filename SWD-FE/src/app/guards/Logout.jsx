import React, { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { AuthActionContext } from "../providers/AuthProvider";

const Logout = () => {
  const navigate = useNavigate();
  const { logout } = useContext(AuthActionContext);

  useEffect(() => {
    logout?.();
    navigate("/", { replace: true });
  }, [navigate, logout]);

  return null;
};

export default Logout;

