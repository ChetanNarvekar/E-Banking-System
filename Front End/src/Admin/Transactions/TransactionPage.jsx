import Footer from "../../Components/Organisms/footer/footer";
import Menubar from "../../Landing_pages/Components/Menubar/menubar";

import AdminAllTransactions from "./transactions";


const TransactionPage = () => {
  return (
    <div>
        <Menubar />
      <div className="admin-home">
        Welcome Admin
      </div>
      <AdminAllTransactions />
      <Footer />
    </div>
  );
};
export default TransactionPage;