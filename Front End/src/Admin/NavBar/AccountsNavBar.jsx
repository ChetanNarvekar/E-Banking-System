import React from 'react';
import { Link } from 'react-router-dom';
import './accountsNavBar.css';

function AccountsNavBar() {
  return (
    <div className="navbar-container">
      <Link to="/customers/status-false" className="nav-link">
        Get Customers with Status False
      </Link>
      <Link to="/accounts/activated" className="nav-link">
        Get Activated Accounts
      </Link>
      <Link to="/accounts/deactivated" className="nav-link">
        Get Deactivated Accounts
      </Link>
      <Link to="/accounts/suspended" className="nav-link">
        Get Suspended Accounts
      </Link>
    </div>
  );
}

export default AccountsNavBar;