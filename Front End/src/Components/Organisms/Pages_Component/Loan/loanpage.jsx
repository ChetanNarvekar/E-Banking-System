import Header from "../../../../Landing_pages/Components/Header/header"
import Footer from "../../footer/footer"
import SideBar from "../../sidebar/sidebar"
import LoanPage from "./loan"

const Loan=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <LoanPage/>
            </div>
            <Footer/>
        </div>
    );
};
export default Loan;
