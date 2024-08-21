import {
  account_summary,
  account_statement,
  profile,
  E_statement,
  spend_analyzer,
  update_kyc,
  fav_links,
} from "../../../../Assets";
import "./style.scss";
//import account_summary from './account_summary.png'

const MyAccountPage = () => {
  return (
    <div className="account-operation">
      {/* left side of account panel start */}
      <div className="left-side">
        <div className="home-account-statement text-flex left">
          <img className="logo-myacccount" src={account_statement} alt=""></img>
          <div>
            <a className="anchor-tag" href="/accountstatement">
              Account Statement
            </a>
          </div>
        </div>
      </div>
      {/* left side end */}

      {/* right side side of control panel */}
      <div className="right-side">
        <div className="home-account-profile text-flex left">
          <img className="logo-myacccount" src={profile} alt=""></img>
          <div>
            <a className="anchor-tag" href="/myprofile">
              Profile
            </a>
          </div>
        </div>
      </div>
      {/* Right side end */}
    </div>
  );
};

export default MyAccountPage;
