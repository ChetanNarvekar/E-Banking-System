import Footer from "../../../../Components/Organisms/footer/footer";
import AccountHeader from "../AccountHeader/AccountHeader";
import DeactivatedAccounts from "./DeactivatedAccount";





const DeActivatedPage = () => {
  return (
    <div>
      <AccountHeader/>
      <DeactivatedAccounts />
      <Footer/>
   
    </div>
  );
};
export default DeActivatedPage;