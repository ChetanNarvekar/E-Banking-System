
import { useNavigate,Link } from "react-router-dom";
import Header from "../../../../Landing_pages/Components/Header/header";
import AccountTypeTable from "./account_type";
import Footer from "../../../../Landing_pages/Components/Footer/footer";
const AccountTypePage = () => {
  return (
 <div>
    <Header/>
    <AccountTypeTable/>
    <Footer/>
 </div>
  );
};
export default AccountTypePage;