import Header from "../../../../Landing_pages/Components/Header/header"
import Footer from "../../footer/footer"
import SideBar from "../../sidebar/sidebar"
import FDPage from "./fdPage"

const FD=()=>{
    return(
        <div>
            <Header/>
            <div className="middle-content-wrapper">
                <SideBar/>
                <FDPage/>
            </div>
            <Footer/>
        </div>
    )
}
export default FD;