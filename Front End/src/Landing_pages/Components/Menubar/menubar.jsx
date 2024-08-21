import "./menubar.scss";
import { jwt } from "../../Assets";
import { useState, useEffect } from "react";
import { useNavigate, Link, useLocation } from "react-router-dom";
import { toast } from "react-toastify";

const Menubar = () => {
  const [date, setDate] = useState(new Date());
  const [userRole, setUserRole] = useState(null); // State to hold the user's role
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // Update date every second
    const intervalId = setInterval(() => {
      setDate(new Date());
    }, 1000);
    return () => clearInterval(intervalId);
  }, []);

  useEffect(() => {
    // Retrieve user role from sessionStorage
    const role = sessionStorage.getItem("role");
    setUserRole(role);
  }, []);

  const isHomePage =
    location.pathname === "/" ||
    location.pathname === "/accounttype" ||
    location.pathname === "/loantype";

  const handleLogout = () => {
    if (window.confirm("Are you sure you want to log out?")) {
      sessionStorage.clear();
      toast.success("Logged out successfully");
      navigate("/login");
    }
  };

  // Check if the user is an admin or superadmin
  const isAdminOrSuperAdmin =
    userRole === "ROLE_ADMIN" || userRole === "ROLE_SUPER_ADMIN";

  return (
    <div className="menubar">
      <div className="left-menu">
        <img src={jwt} alt="JWT Bank Logo" />
        <div className="logo-text">
          JWT <br /> BANK
        </div>
      </div>
      <div className="mid-menu">
        {!isAdminOrSuperAdmin && (
          <div className="services">
            <a href="accounttype">
              <div className="service-tags">Account</div>
            </a>
            <a href="loantype">
              <div className="service-tags">Loan</div>
            </a>
          </div>
        )}
      </div>
      <div className="right-menu">
        <div>
          {isHomePage ? (
            <Link className="login-button" to="/login">LOGIN</Link>
          ) : (
            <button className="login-button" onClick={handleLogout}>
              LOGOUT
            </button>
          )}
        </div>
        <div className="right-menu-date">Date: {date.toLocaleDateString()}</div>
        <div className="right-menu-time">Time: {date.toLocaleTimeString()}</div>
      </div>
    </div>
  );
};

export default Menubar;
