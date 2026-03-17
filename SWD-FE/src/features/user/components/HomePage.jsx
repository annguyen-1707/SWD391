import React from "react";
import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div className="container py-5">
      <h1 className="mb-3">HomePage</h1>
    

      <div className="d-flex flex-wrap gap-2">
        <Link className="btn btn-outline-primary" to="/login">
          Login
        </Link>
        <Link className="btn btn-outline-primary" to="/register">
          Register
        </Link>
        <Link className="btn btn-outline-danger" to="/logout">
          Logout
        </Link>
        <Link className="btn btn-primary" to="/admin/user">
          Admin: User Management
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
