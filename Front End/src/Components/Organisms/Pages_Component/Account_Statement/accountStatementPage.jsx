import Header from "../../../../Landing_pages/Components/Header/header";
import Footer from "../../footer/footer";
import SideBar from "../../sidebar/sidebar";
import AccountStatementTable from "./acc";
import DataTableComponent from "./acc";
import AccountStatementPage from "./account_statement";

const AccountStatement = () => {
  return (
    <div>
      <Header />
      <div className="middle-content-wrapper">
        <SideBar></SideBar>
        <AccountStatementTable />
      </div>
      <Footer/>
    </div>
  );
};
export default AccountStatement;
