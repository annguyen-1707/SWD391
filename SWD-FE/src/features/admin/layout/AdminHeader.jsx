import { AuthActionContext, AuthDataContext } from '@/app/providers/AuthProvider';
import React, { useContext } from 'react';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { Link, NavLink, useNavigate } from 'react-router-dom';

const AdminHeader = () => {

    const { user, theme } = useContext(AuthDataContext);
    const { logout } = useContext(AuthActionContext);
    const navigate = useNavigate();
    const isLoggedIn = !(user === null || user === undefined || Object.keys(user).length === 0);
    const displayName = user?.username || user?.fullName || user?.name || user?.email || "Account";

    return (
        <div className='border-bottom'>
            <Navbar
                expand="lg"
                className={`px-4 py-2 ${theme === "Dark" ? "navbar-dark bg-dark" : "navbar-light bg-white"}`}
            >

                {/* Logo */}
                <Navbar.Brand
                    as={Link}
                    to="/"
                    className="d-flex align-items-center fw-bold fs-3"
                >
                    <img
                        src="/logo/logo-running.png"
                        style={{ height: 35, width: 40, paddingRight: 10 }}
                    />
                    RunWise
                    <span
                        className='ms-1 rounded-circle'
                        style={{
                            backgroundColor: "#6f4ef6",
                            height: 6,
                            width: 6,
                            display: "inline-block"
                        }}
                    />
                </Navbar.Brand>

                <Navbar.Toggle />

                <Navbar.Collapse>

                    {!isLoggedIn ? (
                        <div className="d-flex gap-2 ms-auto">
                            <NavLink className="btn btn-outline-primary" to="/login">
                                Đăng nhập
                            </NavLink>

                            <NavLink className="btn btn-outline-primary" to="/register">
                                Đăng ký
                            </NavLink>
                        </div>
                    ) : (

                        <Nav className="ms-auto">

                            <NavDropdown
                                title={displayName}
                                align="end"
                            >
                                <NavDropdown.Item
                                    onClick={() => {
                                        logout?.();
                                        navigate("/", { replace: true });
                                    }}
                                >
                                    Logout
                                </NavDropdown.Item>

                            </NavDropdown>

                        </Nav>

                    )}

                </Navbar.Collapse>

            </Navbar>
        </div>
    );
};

export default AdminHeader;