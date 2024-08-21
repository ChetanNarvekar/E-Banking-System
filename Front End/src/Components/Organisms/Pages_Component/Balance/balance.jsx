import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios'; // Ensure axios is imported
import { toast } from 'react-toastify'; // Ensure toast is imported
import './style.scss'; // Import the SCSS file
import VerifyTpinModal from "../Modal/verifyTpinModal";
import config from "../../../../config";

const Balance = () => {
    const [balance, setBalance] = useState('');
    const [showModal, setShowModal] = useState(false); // State to control modal visibility
    const [isVerified, setIsVerified] = useState(false); // State to manage verification status

    const navigate = useNavigate();

    const handleVerifySuccess = () => {
        setIsVerified(true);
        fetchBalance(); // Fetch balance after successful verification
    };

    const fetchBalance = async () => {
        try {
            const token = sessionStorage.getItem('token');
            const accId = sessionStorage.getItem('accountNo');
            
            const response = await axios.get(`${config.url}/bank/user/balance/${accId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
    
            const balanceAmount = response.data; // Assuming response.data has the balance
            setBalance(balanceAmount);
            toast.success("Balance fetched successfully");
        } catch (error) {
            toast.error("Failed to get balance");
        }
    };
    

    const OnClick = () => {
        if (!isVerified) {
            setShowModal(true); // Show the modal if TPIN is not verified
        } else {
            fetchBalance(); // Fetch balance if already verified
        }
    };

    const OnUpdate = () => {
        navigate("/myaccount");
    };

    const handleCloseModal = () => {
        setShowModal(false); // Close the modal without verification
    };

    const handleSubmit = (e) => {
        e.preventDefault();
    };

    // Format balance to include ₹ symbol and limit to 2 decimal places
    const formattedBalance = balance ? `₹${parseFloat(balance).toFixed(2)}` : '';

    return (
        <div className='balance'>
            <div className='wrapper'>
                <form onSubmit={handleSubmit}>
                    <h4>Your Account Balance is</h4>
                    <div className='input-box'>
                        <input
                            type='text'
                            placeholder='Balance'
                            value={formattedBalance}
                            onChange={(e) => setBalance(e.target.value)}
                            required
                            readOnly
                        />
                    </div>
                    <div className="tpin-buttons">
                        <button onClick={OnClick} type='button'>Get Balance</button>
                        <button onClick={OnUpdate} type='button'>Cancel</button>
                    </div>
                </form>
            </div>

            <VerifyTpinModal
                show={showModal}
                handleClose={handleCloseModal}
                onVerify={handleVerifySuccess} // Pass handleVerifySuccess to the modal
            />
        </div>
    );
};

export default Balance;
