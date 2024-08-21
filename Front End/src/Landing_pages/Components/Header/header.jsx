import NavbarOperations from "../../../Components/Organisms/navbar_ops/navbar_ops";
import PersonDetails from "../../../Components/Organisms/personal_detail/personal_details";
import Menubar from "../Menubar/menubar";
import "./header.scss";
import { useNavigate, Link,useLocation } from "react-router-dom";
const Header = () => {

  const location = useLocation();
  const shouldRenderNavbarOps = !(
    location.pathname === '/' || 
    location.pathname === '/login' || 
    location.pathname === '/register'||
    location.pathname==='/loantype'||
    location.pathname==='/accounttype'
  );


  
  return (
    <div className="header-wrapper">
      {/* Parent Class */}

      <Menubar />
  
     
      {shouldRenderNavbarOps && <NavbarOperations />}
    </div>

  );
};
export default Header;
