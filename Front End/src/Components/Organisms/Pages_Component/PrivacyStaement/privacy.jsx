import Header from "../../../../Landing_pages/Components/Header/header";
import PrivacyPage from "./privacyPage";
import SideBar from "../../sidebar/sidebar";

import Footer from "../../footer/footer";
const Privacy = () => {
  return (
    <div>
      <Header />
      <div className="middle-content-wrapper">
        <SideBar />
        <PrivacyPage />
      </div>
      <Footer />
    </div>
  );
};
export default Privacy;
