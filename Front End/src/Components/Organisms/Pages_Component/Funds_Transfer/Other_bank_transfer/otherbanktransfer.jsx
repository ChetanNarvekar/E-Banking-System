import Header from "../../../../../Landing_pages/Components/Header/header"
import Footer from "../../../footer/footer"
import SideBar from "../../../sidebar/sidebar"
import PaymentOutForm from "./other_bank_transfer"




const OtherBankTransfer=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <PaymentOutForm/>
            </div>
            <Footer/>
        </div>
    )
}
export default OtherBankTransfer