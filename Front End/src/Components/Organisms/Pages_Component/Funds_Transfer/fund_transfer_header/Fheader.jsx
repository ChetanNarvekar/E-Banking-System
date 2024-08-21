import "./style.scss";
import { useNavigate, Link } from "react-router-dom";
const FundTransferHeader = () => {
  return (
    <div className="fund-transfer">
      <div className="main-fund-transfer">
        <div className="fund-transfer-header">Within Bank</div>
        <div className="fund-transfer-operations">
        <div><Link to={"/ownfundtransfer"}>Within Fox (Own Bank Account)</Link></div>
        <div><Link to={"/withinbanktransfer"}>Accounts of Others </Link></div>
        </div>
      </div>
      <div className="other-fund-transfer">
        <div className="fund-transfer-header">Outside Bank</div>
        <div className="fund-transfer-operations">
          <div><Link to={"/otherbanktransfer"}>Outside Bank Transfer</Link></div>
          
          
        </div>
      </div>


    </div>
  );
};
export default FundTransferHeader;
