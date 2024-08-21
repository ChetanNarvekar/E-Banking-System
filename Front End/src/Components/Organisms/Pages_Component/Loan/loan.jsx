import React, { useState, useEffect } from 'react';
import './style.scss'; // Import the SCSS file
import { useNavigate, Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import axios from 'axios';
import config from '../../../../config';

const LoanPage = () => {
  const [loanAmount, setLoanAmount] = useState('');
  const [loanType, setLoanType] = useState('');
  const [loanDuration, setLoanDuration] = useState('');
  const [accountId, setAccountId] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    
    const accNo = sessionStorage.getItem('accountNo');
    if (accNo) {
      setAccountId(accNo);
    }
  }, []);

  const OnClick = async () => {
    const body = {
      loanAmount,
      loanType,
      loanDuration,
      accountId
    };
    console.log(body);
    if (loanAmount.length === 0) {
      toast.warning('Enter Loan Amount');
    } else if (loanType.length === 0) {
      toast.warning('Enter Loan Type');
    } else if (loanDuration.length === 0) {
      toast.warning('Enter Loan Duration');
    } else if (accountId.length === 0) {
      toast.warning('Enter Account ID');
    } else {
      try {
        const token = sessionStorage.getItem('token'); 
    
        const response = await axios.post(`${config.url}/bank/user/addloanrequest`, body, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
    
        console.log(response.data);
        toast.success("Loan request is successful");
        navigate("/myaccount");
    } catch (error) {
        console.error('Error adding loan request:', error);
        toast.error("Loan request failed");
    }
    
    }
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    // Call the OnClick handler when the form is submitted
    OnClick();
  };

  return (
    <div className="loan-container">
      <div className="loan-card">
        <h2 className="loan-header">Loan Application</h2>
        <form onSubmit={onSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="account-no" className="required">Account No:</label>
              <input
                type="text"
                className="form-control-loan"
                id="account-no"
                name="account-no"
                value={accountId} // Set the value to the state
                placeholder="Enter your account number"
                readOnly // Make the input read-only if desired
              />
            </div>

            <div className="form-group">
              <label htmlFor="loan-type" className="required">Loan Type:</label>
              <select
                onChange={(e) => setLoanType(e.target.value)}
                id="loan-type"
                name="loan-type"
                className="form-control-loan"
              >
                <option value="">Select Loan Type</option>
                <option value="HOME">HOME</option>
                <option value="EDUCATIONAL">EDUCATIONAL</option>
                <option value="car">car</option>
                <option value="PERSONAL">PERSONAL</option>
              </select>
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label htmlFor="loan-amount" className="required">Loan Amount:</label>
              <input
                onChange={(e) => setLoanAmount(e.target.value)}
                type="number"
                className="form-control-loan"
                id="loan-amount"
                name="loan-amount"
                placeholder="Enter loan amount"
              />
            </div>

            <div className="form-group">
              <label htmlFor="duration" className="required">Duration:</label>
              <input
                onChange={(e) => setLoanDuration(e.target.value)}
                type="number"
                className="form-control-loan"
                id="duration"
                name="duration"
              />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label htmlFor="start-date" className="required">Start Date:</label>
              <input
                type="date"
                className="form-control-loan"
                id="start-date"
                name="start-date"
              />
            </div>

            <div className="form-group">
              <label htmlFor="comments">Comments:</label>
              <textarea
                className="form-control-loan"
                id="comments"
                name="comments"
                rows="3"
                placeholder="Any additional comments"
              ></textarea>
            </div>
          </div>

          <div className="form-group form-check">
            <input
              type="checkbox"
              className="form-check-input"
              id="accept-terms"
            />
            <label className="form-check-label" htmlFor="accept-terms">Accept terms and conditions</label>
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">Submit</button>
            <button type="button" className="btn btn-secondary"><Link to={"/loan"}>Back</Link></button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoanPage;








