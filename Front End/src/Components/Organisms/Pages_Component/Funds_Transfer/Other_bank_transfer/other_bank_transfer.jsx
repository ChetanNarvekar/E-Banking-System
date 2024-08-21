import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate, Link } from 'react-router-dom';
import './style.scss';
import VerifyTpinModal from '../../Modal/verifyTpinModal';
import config from '../../../../../config';

const PaymentForm = () => {
  const [formData, setFormData] = useState({
      senderAccountNo: '',
      receiverAccountNo: '',
      amount: '',
      description: '',
      IFSCCode: ''
  });
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
      // Fetch senderAccountNo from session storage and set it to formData
      const savedSenderAccountNo = sessionStorage.getItem('accountNo');
      if (savedSenderAccountNo) {
          setFormData(prevData => ({
              ...prevData,
              senderAccountNo: savedSenderAccountNo
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
        const token = sessionStorage.getItem('token'); 
    
        await axios.post(`${config.url}/bank/user/payment/outside-bank`, body, {
            headers: {
                'Authorization': `Bearer ${token}`, 
                'Content-Type': 'application/json'
            }
        });
    
        toast.success('Payment is successful');
        navigate('/fundtransfer');
    } catch (error) {
        console.log(error);
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
                              className="form-control-other-bank"
                              id="senderAccountNo"
                              name="senderAccountNo"
                              value={formData.senderAccountNo}
                              readOnly // Make the input read-only
                              placeholder="Sender account number"
                          />
                      </div>
                      <div className="form-group">
                          <label htmlFor="receiverAccountNo" className="required">Receiver Account No:</label>
                          <input
                              type="text"
                              className="form-control-other-bank"
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
                              className="form-control-other-bank"
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
                          <label htmlFor="IFSCCode" className="required">IFSC Code:</label>
                          <input
                              type="text"
                              className="form-control-other-bank"
                              id="IFSCCode"
                              name="IFSCCode"
                              value={formData.IFSCCode}
                              onChange={handleChange}
                              placeholder="Enter IFSC code"
                          />
                      </div>
                  </div>
                  <div className="form-group">
                      <label htmlFor="description">Description:</label>
                      <textarea
                          className="form-control-other-bank"
                          id="description"
                          name="description"
                          value={formData.description}
                          onChange={handleChange}
                          rows="3"
                          placeholder="Enter a description (optional)"
                      ></textarea>
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
