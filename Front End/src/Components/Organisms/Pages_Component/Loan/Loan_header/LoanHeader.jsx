import "./style.scss";
import { useNavigate, Link } from "react-router-dom";
const LoanHeader = () => {
  return (
    <div className="loan-wrapper">
      <div className="main-fund-transfer">
        <div className="loan-wrapper-header">Loan</div>
        <div className="loan-wrapper-operations">
        <div><Link to={"/loanR"}>Loan Registration</Link></div>
        <div><Link to={"/collateralAdd"}>Collateral Details</Link></div>
        </div>
      </div>
      <div className="EMI">
        <div className="loan-wrapper-header">EMI Payment</div>
        <div className="loan-wrapper-operations">
          <div><Link to={"/emiPay"}>EMI</Link></div>
       </div>
       </div>
       <div className="Loan">
        <div className="loan-wrapper-header">Loan</div>
        <div className="loan-wrapper-operations">
          <div><Link to={"/getAllCustomerLoan"}>Loan Details</Link></div>
          <div><Link to={"/getAllLoanRequest"}>Loan Request</Link></div>
       </div>
       
       </div>

    </div>
  );
};
export default LoanHeader;
