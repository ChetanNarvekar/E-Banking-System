import React, { useState, useEffect } from 'react';
import './style.scss';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import axios from 'axios';
import config from '../../../../config';

const Collateral = () => {
  const [fields, setFields] = useState({
    requestId: '',
    asset: '',
    value: '',
    description: '',
    acceptTerms: false
  });
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the request ID from session storage and set it in the form
    const reqId = sessionStorage.getItem('Req_id');
    if (reqId) {
      setFields(prevFields => ({
        ...prevFields,
        requestId: reqId
      }));
    }
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFields({
      ...fields,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const validateFields = () => {
    if (!fields.requestId) return 'Please enter Request ID.';
    if (!fields.asset) return 'Please enter Asset.';
    if (!fields.value || fields.value <= 0) return 'Value must be greater than zero.';
    if (!fields.description) return 'Please enter Description.';
    if (!fields.acceptTerms) return 'You must accept the terms and conditions.';
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const errorMessage = validateFields();
    if (errorMessage) {
      toast.warning(errorMessage);
      return;
    }

    const body = {
      requestId: fields.requestId,
      asset: fields.asset,
      value: fields.value,
      description: fields.description,
      acceptTerms: fields.acceptTerms
    };

    try {
      const token = sessionStorage.getItem('token');
      
      const response = await axios.post(`${config.url}/bank/user/collateral/add`, body, {
          headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
          }
      });
      
      toast.success('Collateral added successfully');
      
      // Clear the requestId from session storage
      sessionStorage.removeItem('Req_id');
      
      navigate('/loan');
  } catch (error) {
      toast.error('Loan addition failed');
  }
  
  };

  const handleBack = () => {
    navigate('/loan');
  };

  return (
    <div className='collateral-wrapper-container'>
      <div className="collateral-container">
        <form onSubmit={handleSubmit} className="collateral-form">
          <div className="form-group">
            <label htmlFor="requestId">Request ID</label>
            <input
              type="text"
              id="requestId"
              name="requestId"
              value={fields.requestId}
              onChange={handleChange}
              placeholder="Enter Request ID"
              readOnly
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="asset">Asset</label>
            <input
              type="text"
              id="asset"
              name="asset"
              value={fields.asset}
              onChange={handleChange}
              placeholder="Enter Asset"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="value">Value</label>
            <input
              type="number"
              id="value"
              name="value"
              value={fields.value}
              onChange={handleChange}
              placeholder="Enter Value"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              name="description"
              value={fields.description}
              onChange={handleChange}
              placeholder="Enter Description"
              required
            />
          </div>

          <div className="form-group-checkbox">
            <label>
              <input
                type="checkbox"
                name="acceptTerms"
                checked={fields.acceptTerms}
                onChange={handleChange}
                required
              />
              I accept the terms and conditions
            </label>
          </div>

          <div className="form-buttons">
            <button type="submit" className="submit-btn">Submit</button>
            <button type="button" className="back-btn" onClick={handleBack}>Back</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Collateral;



