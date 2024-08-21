
import Footer from "../../../Components/Organisms/footer/footer";

import AdminLoanHeader from "../../AdminHeaderLoan";
import LoanList from "./LoanDetails";



const LoanListPage = () => {
  return (
    <div>
      <AdminLoanHeader/>
      < LoanList/>
      <Footer/>
    </div>
  );
};
export default LoanListPage;