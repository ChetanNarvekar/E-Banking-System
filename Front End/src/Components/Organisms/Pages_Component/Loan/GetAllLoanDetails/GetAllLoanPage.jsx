import Header from "../../../../../Landing_pages/Components/Header/header";
import Footer from "../../../footer/footer";
import SideBar from "../../../sidebar/sidebar";
import GetAllLoanDetails from "./GetAllLoanDetails";


const GetAllLoanPage = () => {
  return (
    <div>
      <Header />
      <div className="middle-content-wrapper">
        <SideBar/>
        <GetAllLoanDetails />
      </div>
      <Footer/>
    </div>
  );
};
export default GetAllLoanPage;