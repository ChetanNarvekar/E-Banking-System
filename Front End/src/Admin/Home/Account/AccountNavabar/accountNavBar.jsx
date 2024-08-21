import "./style.scss";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
const AccountNavBar = () => {
  const navigate = useNavigate();
  return (
    <div className="accountNavBar">
      <div>
        <Link to={"/accounts/pending"}>Pending Accounts</Link>
      </div>
      <div>
        <Link to={"/accounts/activated"}>Active Accounts</Link>
      </div>

      <div>
        <Link to={"/accounts/deactivated"}>Deactivated Accounts </Link>
      </div>
      <div>
        <Link to={"/accounts/suspended"}>Suspended Accounts</Link>
      </div>
      <div>
        <Link to={"/adminHome"}>Home</Link>
      </div>
      <div>
        <Link to={"/accounts/depositPage"}>Deposit</Link>
      </div>
      
    </div>
  );
};
export default AccountNavBar;