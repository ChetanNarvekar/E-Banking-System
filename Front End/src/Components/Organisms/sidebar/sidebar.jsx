import "./style.scss";
import { useNavigate, Link } from "react-router-dom";

const SideBar = () => {
  return (
    <div className="sidebar-wrapper">
      <div className="buttons-header">Quick Links</div>
      
      <div className="buttons-section">
       
        <button className="sidebar-button"><Link to={"/accountstatement"}>Account Statement</Link></button>
        <button className="sidebar-button"><Link to={"/myprofile"}>Profile</Link></button>
    
        
      </div>
      
      
      <div className="buttons-header">Advertisements</div>
      <div className="sidebar-advertisement">
    
      </div>
    </div>
  );
};
export default SideBar;
