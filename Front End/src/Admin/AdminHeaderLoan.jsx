import Footer from "../Components/Organisms/footer/footer";
import Menubar from "../Landing_pages/Components/Menubar/menubar";
import NavbarLaonOperations from "./NavbarOperationLoan/navbar_ops/navbar_loan_ops";

const AdminLoanHeader = () => {
  return (
    <div>
      <Menubar/>
      <NavbarLaonOperations />
   
    </div>
  );
};
export default AdminLoanHeader;