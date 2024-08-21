

import Footer from "../../Components/Organisms/footer/footer";
import Menubar from "../../Landing_pages/Components/Menubar/menubar";
import './adminHome.scss'
import AdminHome from "./home";

const AdminHomePage = () => {
  return (
    <div>
      <Menubar />
      <div className="admin-home">
        Welcome Admin
      </div>
      <AdminHome />
      <Footer />
    </div>
  );
};
export default AdminHomePage;
