import React from 'react';
import { Link } from 'react-router-dom';
import './loanNavBar.css'; // Ensure you create this CSS file for styling
import Header from '../../Landing_pages/Components/Header/header';
import Footer from '../../Components/Organisms/footer/footer';

function LoanNavBar() {
  return (
    <>
    <Header/>
      <div className="navbar-container">
      <Link to="/status-pending" className="nav-link">
        Get Loan Requests
      </Link>
      <Link to="/approve-decline" className="nav-link">
        Update Loan Requests
      </Link>
      <Link to="LoanList" className="nav-link">
        All Loans
      </Link>
      <Link to="declined" className="nav-link">
        Declined Loans
      </Link>
    </div>
    <Footer/>
    </>
  
  );
}

export default LoanNavBar;
