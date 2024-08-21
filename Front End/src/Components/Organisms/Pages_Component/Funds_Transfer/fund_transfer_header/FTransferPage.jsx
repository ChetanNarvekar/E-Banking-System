import Header from "../../../../../Landing_pages/Components/Header/header"
import Footer from "../../../footer/footer"
import SideBar from "../../../sidebar/sidebar"
import FundTransferHeader from "./Fheader"

const FundTransfer=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <FundTransferHeader/>
            </div>
            <Footer/>
        </div>
    )
}
export default FundTransfer