import Header from "../../../../Landing_pages/Components/Header/header";
import Footer from "../../footer/footer";
import SideBar from "../../sidebar/sidebar";
import MiddleContent from "./middleContent";

import MyAccountPage from "./myAccount";

const MyAccount =()=>{
    return(
        <>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <MyAccountPage/>
            </div>
         
            <Footer/>

        </>
    )

}
export default MyAccount;