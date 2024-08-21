import Header from "../../../../../Landing_pages/Components/Header/header"
import Footer from "../../../footer/footer"

import SideBar from "../../../sidebar/sidebar"
import PaymentForm from "./within_same_bank_different_account"


const WithinBankTransfer =()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <PaymentForm/>
            </div>
            <Footer/>
            
        </div>
    )
}
export default WithinBankTransfer