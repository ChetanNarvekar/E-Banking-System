import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import './BankDetails.css';
import config from '../../../config'; // Make sure to set this path correctly

const BankDetails = () => {
    const [bankDetails, setBankDetails] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchBankDetails();
      }, []);
    
      const fetchBankDetails = async () => {
        try {
          const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
          const response = await axios.get(`${config.url}/bank/superadmin/getBankDetails`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
            }
          });
          setBankDetails(response.data);
          setLoading(false); // Stop the loading state after fetching data
        } catch (error) {
          toast.error('Failed to fetch bank details');
          setLoading(false); // Stop the loading state even if there’s an error
        }
      };

    return (
        <div className="bank-details-page">
            <h2>Bank Details</h2>
            <div className="bank-details">
                <div className="detail-row">
                    <strong>Bank Branch ID:</strong> {bankDetails.bankBranchId}
                </div>
                <div className="detail-row">
                    <strong>Bank Name:</strong> {bankDetails.bankName}
                </div>
                <div className="detail-row">
                    <strong>Contact Email:</strong> {bankDetails.contactEmail}
                </div>
                <div className="detail-row">
                    <strong>Fund Available:</strong> ₹{bankDetails.fundAvailable}
                </div>
                <div className="detail-row">
                    <strong>Fund Received:</strong> ₹{bankDetails.fundReceived}
                </div>
                <div className="detail-row">
                    <strong>Fund to Pay:</strong> ₹{bankDetails.fundToPay}
                </div>
                <div className="detail-row">
                    <strong>Loan Disbursed:</strong> ₹{bankDetails.loanDisbursed}
                </div>
                <div className="detail-row">
                    <strong>Loan Recovered:</strong> ₹{bankDetails.loanRecovered}
                </div>
                <div className="detail-row">
                    <strong>Loan Expected:</strong> ₹{bankDetails.loanExpected}
                </div>
            </div>
        </div>
    );
};

export default BankDetails;
