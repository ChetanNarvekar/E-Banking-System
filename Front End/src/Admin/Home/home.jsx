import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaExchangeAlt, FaMoneyCheckAlt, FaWallet, FaUserShield } from 'react-icons/fa';
import './home.css'; 
import VerifyTpinModal from '../../Components/Organisms/Pages_Component/Modal/verifyTpinModal';

function AdminHome() {
  const [showModal, setShowModal] = useState(false);
  const [targetPath, setTargetPath] = useState('');
  const navigate = useNavigate();
  const role = sessionStorage.getItem("role");

  const handlePasswordVerification = (path) => {
    if (path === '/super-admin' && role !== 'ROLE_SUPER_ADMIN') {
      alert('You do not have permission to access this page.');
      return;
    }
    setTargetPath(path);
    setShowModal(true);
  };

  const handleVerify = () => {
    navigate(targetPath);
    setShowModal(false);
  };

  return (
    <div className="home-container">
      {/* <h2 className='page-title'>Hello Admin!</h2> */}
      <div className="home-options">
        <button className="home-option" onClick={() => handlePasswordVerification('/myprofile')}>
          <FaUser size={50} />
          <span>Profile</span>
        </button>
        <button className="home-option" onClick={() => handlePasswordVerification('/Atransactions')}>
          <FaExchangeAlt size={50} />
          <span>Transactions</span>
        </button>
        <button className="home-option" onClick={() => handlePasswordVerification('/loans/status-pending')}>
          <FaMoneyCheckAlt size={50} />
          <span>Loans</span>
        </button>
        <button className="home-option" onClick={() => handlePasswordVerification('/accounts/pending')}>
          <FaWallet size={50} />
          <span>Accounts</span>
        </button>
        {role === 'ROLE_SUPER_ADMIN' && (
          <button className="admin-home-option" onClick={() => handlePasswordVerification('/enable-admin')}>
            <FaUserShield size={50} />
            <span>Super Admin</span>
          </button>
        )}
      </div>

      {/* VerifyTpinModal Component */}
      <VerifyTpinModal 
        show={showModal} 
        handleClose={() => setShowModal(false)} 
        onVerify={handleVerify} 
      />
    </div>
  );
}

export default AdminHome;


