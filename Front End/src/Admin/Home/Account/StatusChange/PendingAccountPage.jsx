import Footer from "../../../../Components/Organisms/footer/footer";
import AccountHeader from "../AccountHeader/AccountHeader";
import CustomersWithStatusFalse from "./CustomersWithStatusFalse";





const PendingAccountPage = () => {
  return (
    <div>
      <AccountHeader/>
      <CustomersWithStatusFalse />
      <Footer/>
   
    </div>
  );
};
export default PendingAccountPage;