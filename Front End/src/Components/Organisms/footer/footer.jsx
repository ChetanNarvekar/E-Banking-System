import "./style.scss";
import { useNavigate,Link } from "react-router-dom";
const Footer = () => {
  return (
    <div className="login-footer-wrapper">
      <div>Copyright@ Ebank</div>

      <div><Link to={"/disclaimer"}>Disclaimer</Link> | </div>

      <div><Link to={"/privacy"}>Privacy Policy</Link> </div>
    </div>
  );
};
export default Footer;
