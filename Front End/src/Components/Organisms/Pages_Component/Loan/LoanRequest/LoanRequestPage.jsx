import { useNavigate,Link } from "react-router-dom";
import Header from "../../../../../Landing_pages/Components/Header/header";
import Footer from "../../../footer/footer";
import LoanRequestsTable from "./LoanRequest";
import SideBar from "../../../sidebar/sidebar";


const LoanRequestPage = () => {
  return (
 <div>
    <Header/>
    <div className="middle-content-wrapper">
        <SideBar/>
        <LoanRequestsTable />
      </div>
    <Footer/>
 </div>
  );
};
export default LoanRequestPage;