import Header from "../../../../Landing_pages/Components/Header/header"
import Footers from "../../footer/footer"

import SideBar from "../../sidebar/sidebar"
import DisclaimerPage from "./disclaimerPage"

const Disclaimer=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <DisclaimerPage/>
            </div>
           <Footers/>

       
        </div>
    )
}
export default Disclaimer