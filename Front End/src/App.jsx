import "./App.css";
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import { Route, Routes } from "react-router-dom";
import OwnFundTransfer from "./Components/Organisms/Pages_Component/Funds_Transfer/own_fund_transfer/own_fund_transfer_page.jsx";
import MyAccount from "./Components/Organisms/Pages_Component/MyAccount/myaccountPage.jsx";
import AccountStatement from "./Components/Organisms/Pages_Component/Account_Statement/accountStatementPage.jsx";
import FundTransfer from "./Components/Organisms/Pages_Component/Funds_Transfer/fund_transfer_header/FTransferPage.jsx";
import OtherBankTransfer from "./Components/Organisms/Pages_Component/Funds_Transfer/Other_bank_transfer/otherbanktransfer.jsx";
import WithinBankTransfer from "./Components/Organisms/Pages_Component/Funds_Transfer/within_same_bank_different_account/withinsamebanktransfer.jsx";
import Privacy from "./Components/Organisms/Pages_Component/PrivacyStaement/privacy.jsx";

import FD from "./Components/Organisms/Pages_Component/FD/fd.jsx";

import MyProfile from "./Components/Organisms/Pages_Component/myprofile/myprofilepage.jsx";
import Home from "./Landing_pages/Pages/Home/home.jsx";
import Disclaimer from "./Components/Organisms/Pages_Component/Disclaimer/disclaimer.jsx";
import Login from "./Components/Organisms/Pages_Component/Login/login.jsx";



import LoanHeaderPage from "./Components/Organisms/Pages_Component/Loan/Loan_header/Loan_wrapper_page.jsx";

import CollateralPage from "./Components/Organisms/Pages_Component/Collateral/Collateral_Page.jsx";
import Loan from "./Components/Organisms/Pages_Component/Loan/loanpage.jsx";


import BalancePage from "./Components/Organisms/Pages_Component/Balance/balancePage.jsx";
import RegisterForm from "./Components/Organisms/Pages_Component/Register/register.jsx";
import EMIPage from "./Components/Organisms/Pages_Component/EMI/emiPage.jsx";


import AccountsNavBar from "./Admin/NavBar/AccountsNavBar.jsx";
import CustomersWithStatusFalse from "./Admin/Home/Account/StatusChange/CustomersWithStatusFalse.jsx";
import DeactivatedAccounts from "./Admin/Home/Account/DeactivatedAccount/DeactivatedAccount.jsx";
import ActivatedAccounts from "./Admin/Home/Account/Activated/ActivatedAccount.jsx";
import GetAllLoanPage from "./Components/Organisms/Pages_Component/Loan/GetAllLoanDetails/GetAllLoanPage.jsx";



import AccountTypePage from "./Components/Organisms/Pages_Component/Account_type/AccountTypePage.jsx";
import LoanTypePage from "./Components/Organisms/Pages_Component/Loan/LoanTypes/LoanTypePage.jsx";
import LoanRequestsTable from "./Components/Organisms/Pages_Component/Loan/LoanRequest/LoanRequest.jsx";
import LoanRequestPage from "./Components/Organisms/Pages_Component/Loan/LoanRequest/LoanRequestPage.jsx";
import LoanTypeTable from "./Components/Organisms/Pages_Component/Loan/LoanTypes/LoanTypes.jsx";
import SuperAdminDashboard from "./Admin/Home/Super_Admin/SuperAdminDashboard";
import ManageAdmins from "./Admin/Home/Super_Admin/ManageAdmins";
import BankDetails from "./Admin/Home/Super_Admin/BankDetails";
import AddAdmins from "./Admin/Home/Super_Admin/AddAdmins";
import EnableDisabledAdmins from "./Admin/Home/Super_Admin/EnableDisabledAdmins";
import SuspendedAccounts from "./Admin/Home/Account/Suspended/SuspendedAccounts";
import LoanList from "./Admin/Loan/LoanDetails/LoanDetails.jsx";

import LoanRequests from "./Admin/Loan/LoanApproval/LoanApprovalDeclined.jsx";
import LoanNavBar from "./Admin/NavBar/LoanNavBar.jsx";
import FundDistributionChart from "./Admin/Transactions/FundDistributionsChart/funddistributions_chart.jsx";
import LoanRequestsDeclined from "./Admin/Loan/Declined_Loan/DeclinedLoan.jsx";
import AdminHomePage from "./Admin/Home/AdminHomePage.jsx";
import TransactionPage from "./Admin/Transactions/TransactionPage.jsx";

import LoanStatusPendingPage from "./Admin/Loan/ChangedToPending/LoanStatusPendingPage.jsx";
import PendingListPage from "./Admin/Loan/LoanApproval/PendingListPage.jsx";
import LoanListPage from "./Admin/Loan/LoanDetails/LoanListPage.jsx";
import DeclinedLoanPage from "./Admin/Loan/Declined_Loan/DeclinedLoanPage.jsx";
import EnableAdminPage from "./Admin/Home/Super_Admin/EnableAdminPage.jsx";
import AddAdminPage from "./Admin/Home/Super_Admin/AddAdminPage.jsx";
import ManageAdminPage from "./Admin/Home/Super_Admin/Manage-Admin-Page.jsx";
import BankDetailsPage from "./Admin/Home/Super_Admin/BankDetailsPage.jsx";
import DeActivatedPage from "./Admin/Home/Account/DeactivatedAccount/DeactivatedPage.jsx";
import ActivatedPage from "./Admin/Home/Account/Activated/ActivatedPage.jsx";
import SuspendedAccountPage from "./Admin/Home/Account/Suspended/SuspendedAccountPage.jsx";
import PendingAccountPage from "./Admin/Home/Account/StatusChange/PendingAccountPage.jsx";
import DepositPage from "./Admin/Home/Account/Deposit/Fund_received_from_outside/DepositPage.jsx";





function App() {
  return (
    <div className="App">
      <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      
      <Route path="/disclaimer" element={<Disclaimer />} />
      <Route path="/myprofile" element={<MyProfile />} />
      
      <Route path="/fd" element={<FD/>} />
      <Route path="/register" element={<RegisterForm />} />
      <Route path="/privacy" element={<Privacy />} />
      <Route path='/myaccount' element={<MyAccount />} /> 
      <Route path='/ownfundtransfer' element={<OwnFundTransfer />} />
      <Route path="/accountstatement" element={<AccountStatement />} />
      <Route path="/fundtransfer" element={<FundTransfer />} />
      <Route path="/otherbanktransfer" element={<OtherBankTransfer />} />
      <Route path="/withinbanktransfer" element={<WithinBankTransfer />} />
    
      <Route path="/loan" element={< LoanHeaderPage/>} />
      <Route path="/loanR" element={<Loan/>} />
      <Route path='/collateralAdd' element={<CollateralPage />} />
      <Route path="/withinBankTransfer" element={<WithinBankTransfer />} />
   
      <Route path="/checkBalance" element={<BalancePage />} />
      <Route path="/emiPay" element={<EMIPage/>} />
      <Route path="/adminHome" element={<AdminHomePage/>} />
      <Route path="/Atransactions" element={<TransactionPage/>} />
      <Route path="/accounts/pending" element={<PendingAccountPage/>} />
      {/* <Route path="/customers/status-false" element={<PendingAccountPage/>} /> */}
      <Route path="/accounts/deactivated" element={<DeActivatedPage/>} />
      <Route path="/accounts/activated" element={<ActivatedPage/>} />
      <Route path="/accounts/suspended" element={<SuspendedAccountPage/>} />
      <Route path="/getAllCustomerLoan" element={<GetAllLoanPage/>} />

      <Route path="/loantype" element={<LoanTypePage/>} />
      <Route path="/accounttype" element={<AccountTypePage/>} />
      <Route path="/getAllLoanRequest" element={<LoanRequestPage/>} />
     

      <Route path="/l" element={<LoanTypeTable/>} />
      <Route path="/enable-admin" element={<EnableAdminPage/>} />
      <Route path="/manage-admins" element={<ManageAdminPage/>} />
      <Route path="/add-admin" element={<AddAdminPage/>} />
      {/* <Route path="/enable-admin" element={<EnableAdminPage/>} /> */}
      <Route path="/bank-details" element={<BankDetailsPage/>} />
      
      <Route path="/loans/LoanList" element={<LoanListPage/>} />
      <Route path="/loans/status-pending" element={<LoanStatusPendingPage/>} />
      <Route path="/loans/approve-decline" element={<PendingListPage/>} />
      {/* <Route path="/loans" element={<LoanStatusPendingPage/>} /> */}
      <Route path="/loans/declined" element={<DeclinedLoanPage/>} />
      <Route path="/accounts/depositPage" element={<DepositPage/>} />
     

      
      
      </Routes>
          
          <ToastContainer/>
    </div>
  );
}

export default App;
