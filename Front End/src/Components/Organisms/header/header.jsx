import "./style.scss";
import { bank_logo, home_logo, rewards_logo } from "../../../Assets";
const Header = () => {
  return (
    <div className="header-wrapper">
      <div className="header-left-wrapper">
        <img src={bank_logo} alt="" className="header-bank-logo-img"></img>
        <div>Fox Bank</div>
      </div>
      <div className="header-right-wrapper">
        <img src={home_logo} alt="" className="header-home-logo-img"></img>
        <div className="header-right-text-box"><a className="nav-tabs" href="gg" alt="">About Us</a></div>
        <div className="header-right-text-box"><a className="nav-tabs" href="kk" alt="">Fox Corner</a></div>
        <div className="header-right-text-box"><a className="nav-tabs" href="gg" alt="">Contact Us</a></div>
        <div>
          <img src={rewards_logo} alt="" className="header-rewards-logo-img" />
        </div>
      </div>
    </div>
  );
};
export default Header;
