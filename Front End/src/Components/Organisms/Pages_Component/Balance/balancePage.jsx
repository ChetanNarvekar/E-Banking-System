
import Header from "../../../../Landing_pages/Components/Header/header";
import Footer from "../../footer/footer";
import SideBar from "../../sidebar/sidebar";
import Balance from "./balance";



const BalancePage =()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <Balance/>
            </div>
           {/* <MiddleContent/> */}
            <Footer/>

        </div>
    )

}
export default BalancePage;