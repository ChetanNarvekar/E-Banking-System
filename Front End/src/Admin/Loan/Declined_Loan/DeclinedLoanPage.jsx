import Footer from "../../../Components/Organisms/footer/footer";
import AdminLoanHeader from "../../AdminHeaderLoan";
import LoanRequestsDeclined from "./DeclinedLoan";




const DeclinedLoanPage = () => {
  return (
    <div>
      <AdminLoanHeader/>
      < LoanRequestsDeclined/>
      <Footer/>
    </div>
  );
};
export default DeclinedLoanPage;