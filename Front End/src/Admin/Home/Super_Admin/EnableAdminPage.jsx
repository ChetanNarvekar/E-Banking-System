import Footer from "../../../Components/Organisms/footer/footer";
import EnableDisabledAdmins from "./EnableDisabledAdmins";
import SuperAdminHeader from "./SuperAdminHeader";
import NavbarSuperAdmin from "./SuperAdminNavbar/NavbarSuperAdmin";



const EnableAdminPage = () => {
  return (
    <div>
      <SuperAdminHeader />
      < EnableDisabledAdmins/>
      <Footer/>
    </div>
  );
};
export default EnableAdminPage;