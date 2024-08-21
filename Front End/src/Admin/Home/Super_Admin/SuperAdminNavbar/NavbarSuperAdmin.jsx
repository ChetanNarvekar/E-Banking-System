import "./style.scss";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
const NavbarSuperAdmin = () => {
  return (
    <div className="navbar-super-admin">
      <div>
        <Link to={"/enable-admin"}>Enable Admin</Link>
      </div>
      <div>
        <Link to={"/add-admin"}>Add Admin</Link>
      </div>

      <div>
        <Link to={"/manage-admins"}>Manage Admin</Link>
      </div>
      <div>
        <Link to={"/bank-details"}>Bank Details</Link>
      </div>
      <div>
        <Link to={"/adminHome"}>Home</Link>
      </div>
    </div>
  );
};
export default NavbarSuperAdmin;
