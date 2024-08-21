import React, { useState, useEffect } from 'react';
import './style.scss';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import VerifyTpinModal from '../../Modal/verifyTpinModal';
import config from '../../../../../config';

const PaymentForm = () => {
  const [formData, setFormData] = useState({
      senderAccountNo: '',
      receiverAccountNo: '',
      amount: '',
      description: '',
      type: 'DEBIT', 
      IFSCCode: ''
  });
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch senderAccountNo from session storage and set it to formData
    const storedSenderAccountNo = sessionStorage.getItem('accountNo');
    if (storedSenderAccountNo) {
        setFormData((prevData) => ({
            ...prevData,
            senderAccountNo: storedSenderAccountNo
        }));
    }
  }, []);

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const processPayment = async () => {
      const body = {
          senderAccountNo: formData.senderAccountNo,
          receiverAccountNo: formData.receiverAccountNo,
          amount: formData.amount,
          description: formData.description,
          IFSCCode: formData.IFSCCode
      };

      try {
        const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
    
        await axios.post(`${config.url}/bank/user/payment/within-bank`, body, {
            headers: {
                'Authorization': `Bearer ${token}`, // Add the token in the Authorization header
                'Content-Type': 'application/json'
            }
        });
    
        toast.success('Payment is successful');
        navigate('/fundtransfer');
    } catch (error) {
        console.error('Error processing payment:', error); // Log the error for debugging
        toast.error('Payment is unsuccessful');
    }
    
  };

  const handleVerifySuccess = () => {
      processPayment(); 
  };

  const handleSubmit = (e) => {
      e.preventDefault();
      handleShowModal(); 
  };

  const handleChange = (e) => {
      setFormData({
          ...formData,
          [e.target.name]: e.target.value,
      });
  };

  return (
      <div className="payment-container">
          <div className="payment-card">
              <h2 className="payment-header">Payment to Other Account</h2>
              <form onSubmit={handleSubmit}>
                  <div className="form-row">
                      <div className="form-group">
                          <label htmlFor="senderAccountNo" className="required">Sender Account No:</label>
                          <input
                              type="text"
                              className="form-control-other"
                              id="senderAccountNo"
                              name="senderAccountNo"
                              value={formData.senderAccountNo}
                              readOnly
                              placeholder="Sender account number"
                          />
                      </div>

                      <div className="form-group">
                          <label htmlFor="receiverAccountNo" className="required">Receiver Account No:</label>
                          <input
                              type="text"
                              className="form-control-other"
                              id="receiverAccountNo"
                              name="receiverAccountNo"
                              value={formData.receiverAccountNo}
                              onChange={handleChange}
                              placeholder="Enter receiver account number"
                          />
                      </div>
                  </div>

                  <div className="form-row">
                      <div className="form-group">
                          <label htmlFor="amount" className="required">Amount:</label>
                          <input
                              type="number"
                              className="form-control-other"
                              id="amount"
                              name="amount"
                              value={formData.amount}
                              onChange={handleChange}
                              placeholder="Enter amount"
                              min="0.01"
                              step="0.01"
                          />
                      </div>

                      <div className="form-group">
                          <label htmlFor="type" className="required">Transaction Type:</label>
                          <select
                              id="type"
                              name="type"
                              className="form-control-other"
                              value={formData.type}
                              onChange={handleChange}
                          >
                              <option value="">Select Transaction Type</option>
                              <option value="CREDIT">CREDIT</option>
                              <option value="DEBIT">DEBIT</option>
                              <option value="EMI">EMI</option>
                          </select>
                      </div>
                  </div>

                  <div className="form-row">
                      <div className="form-group">
                          <label htmlFor="IFSCCode" className="required">IFSC Code:</label>
                          <input
                              type="text"
                              className="form-control-other"
                              id="IFSCCode"
                              name="IFSCCode"
                              value={formData.IFSCCode}
                              onChange={handleChange}
                              placeholder="Enter IFSC code"
                          />
                      </div>

                      <div className="form-group">
                          <label htmlFor="description">Description:</label>
                          <textarea
                              className="form-control-other"
                              id="description"
                              name="description"
                              value={formData.description}
                              onChange={handleChange}
                              rows="3"
                              placeholder="Enter a description (optional)"
                          ></textarea>
                      </div>
                  </div>

                  <div className="form-actions">
                      <button type="submit" className="btn btn-primary">Submit</button>
                      <button type="button" className="btn btn-secondary"><Link to={"/fundtransfer"}>Back</Link></button>
                  </div>
              </form>
          </div>
          <VerifyTpinModal
              show={showModal}
              handleClose={handleCloseModal}
              onVerify={handleVerifySuccess}
          />
      </div>
  );
};

export default PaymentForm;
