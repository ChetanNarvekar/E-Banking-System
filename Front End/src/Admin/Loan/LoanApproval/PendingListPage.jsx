import Footer from "../../../Components/Organisms/footer/footer";

import AdminLoanHeader from "../../AdminHeaderLoan";
import LoanRequests from "./LoanApprovalDeclined";


const PendingListPage = () => {
  return (
    <div>
      <AdminLoanHeader />
      < LoanRequests/>
      <Footer/>
    </div>
  );
};
export default PendingListPage;