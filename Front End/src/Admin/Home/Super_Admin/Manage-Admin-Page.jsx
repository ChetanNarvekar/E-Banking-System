import Footer from "../../../Components/Organisms/footer/footer";
import ManageAdmins from "./ManageAdmins";
import SuperAdminHeader from "./SuperAdminHeader";





const ManageAdminPage = () => {
  return (
    <div>
      <SuperAdminHeader />
      < ManageAdmins/>
      <Footer/>
    </div>
  );
};
export default ManageAdminPage;