import Header from "../../../../../Landing_pages/Components/Header/header"
import Footer from "../../../footer/footer"
import SideBar from "../../../sidebar/sidebar"
import LoanHeader from "./LoanHeader"


const LoanHeaderPage=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <LoanHeader/>
            </div>
            <Footer/>
        </div>
    )
}
export default LoanHeaderPage;