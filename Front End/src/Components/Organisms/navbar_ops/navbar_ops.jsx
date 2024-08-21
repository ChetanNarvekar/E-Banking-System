import './style.scss'
import {Link} from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
const NavbarOperations =()=>{
    const navigate =useNavigate();
    return(
        <div className="navbar-opeartions">
            <div><Link to={"/myaccount"}>My Account</Link></div>
            <div><Link to={"/fundtransfer"}>Transfer Funds</Link></div>
            
            <div><Link to={"/accountstatement"}>Account Statement</Link></div>
            <div><Link to={"/loan"}>Loan</Link></div>
            <div><Link to={"/checkbalance"}>Check Balance</Link></div>

        </div>
    )
}
export default NavbarOperations