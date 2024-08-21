import Header from "../../../../Landing_pages/Components/Header/header"
import Footer from "../../footer/footer"
import SideBar from "../../sidebar/sidebar"
import MyProfilePage from "./myprofile"

const MyProfile=()=>{
    return(
       <div>
       <Header/>
       <div className="middle-content-wrapper">
        <SideBar/>
        <MyProfilePage/>
       </div>
       <Footer/>
       
       </div>
    )
}
export default MyProfile