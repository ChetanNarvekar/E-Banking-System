import SideBar from "../../sidebar/sidebar"
import MyAccount from "./myaccountPage"

const MiddleContent = ()=>{
    return(
        <div className="middle-content-wrapper">
            <SideBar/>
            <MyAccount/>
        </div>
    )
}
export default MiddleContent