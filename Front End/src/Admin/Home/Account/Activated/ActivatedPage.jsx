import Footer from "../../../../Components/Organisms/footer/footer";
import AccountHeader from "../AccountHeader/AccountHeader";
import ActivatedAccounts from "./ActivatedAccount";




const ActivatedPage = () => {
  return (
    <div>
      <AccountHeader/>
      <ActivatedAccounts />
      <Footer/>
   
    </div>
  );
};
export default ActivatedPage;