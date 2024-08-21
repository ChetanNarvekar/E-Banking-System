import Header from "../../../../Landing_pages/Components/Header/header";
import Footer from "../../footer/footer";
import SideBar from "../../sidebar/sidebar";
import EmiPay from "./newEmi";


const EMIPage=()=>{
    return(
        <div>
        <Header/>
        <div className="middle-content-wrapper">
            <SideBar/>
            <EmiPay/>
        </div>

        <Footer/>

    </div>
    )
}
export default EMIPage;