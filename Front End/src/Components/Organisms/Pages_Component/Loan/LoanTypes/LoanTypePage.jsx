import { useNavigate,Link } from "react-router-dom";

import LoanTypeTable from "./LoanTypes";
import Header from "../../../../../Landing_pages/Components/Header/header";
import Footer from "../../../../../Landing_pages/Components/Footer/footer";
const LoanTypePage = () => {
  return (
 <div>
    <Header/>
    <LoanTypeTable/>
    <Footer/>
 </div>
  );
};
export default LoanTypePage;