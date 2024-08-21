import Header from "../../../../Landing_pages/Components/Header/header";
import Footer from "../../footer/footer";
import SideBar from "../../sidebar/sidebar";
import Collateral from "./collateral";

const CollateralPage=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <Collateral/>
            </div>
            <Footer/>
        </div>
    )
}
export default CollateralPage;