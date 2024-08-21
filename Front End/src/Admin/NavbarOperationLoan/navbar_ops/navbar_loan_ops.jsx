import "./style.scss";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
const NavbarLaonOperations = () => {
  const navigate = useNavigate();
  return (
    <div className="navbar-loan-opeartions">
      <div>
        <Link to={"/loans/status-pending"}>Get Loan Requests</Link>
      </div>
      <div>
        <Link to={"/loans/approve-decline"}>Update Loan Requests</Link>
      </div>

      <div>
        <Link to={"/loans/LoanList"}>All Loans</Link>
      </div>
      <div>
        <Link to={"/loans/declined"}>Declined Loans</Link>
      </div>
      <div>
        <Link to={"/adminHome"}>Home</Link>
      </div>
    </div>
  );
};
export default NavbarLaonOperations;
