

import Header from "../../../../../Landing_pages/Components/Header/header"
import Footer from "../../../footer/footer"

import SideBar from "../../../sidebar/sidebar"


import PaymentForm from "./own_fund_transfer"


const OwnFundTransfer=()=>{
    return(
        <div>
            <Header/>
           
            <div className="middle-content-wrapper"><SideBar/>
            <PaymentForm/></div>
            <Footer/>
           
            
        </div>
    )
}
export default OwnFundTransfer