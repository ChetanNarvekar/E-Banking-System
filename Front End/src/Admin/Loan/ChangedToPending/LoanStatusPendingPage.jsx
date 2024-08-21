import Footer from "../../../Components/Organisms/footer/footer";
import AdminLoanHeader from "../../AdminHeaderLoan";
import LoanRequestsWithStatus from "./StatusChangeToPending";

const LoanStatusPendingPage = () => {
  return (
    <div>
      <AdminLoanHeader />
      < LoanRequestsWithStatus/>
      <Footer/>
    </div>
  );
};
export default LoanStatusPendingPage;