import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import './style.scss';
import config from '../../../../config';
import VerifyTpinModal from '../Modal/verifyTpinModal';
import { FaEye, FaEyeSlash } from 'react-icons/fa';

const CustomerForm = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    middleName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: '',
    dateOfBirth: '',
    gender: '',
    adharNo: '',
    panNo: '',
    accountType: '',
    tpin: ''
  });

  const [originalData, setOriginalData] = useState({});
  const [showModal, setShowModal] = useState(false);
  const [currentAction, setCurrentAction] = useState(null);
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);
  const [tpinVisible, setTpinVisible] = useState(false);
  const customerId = sessionStorage.getItem('customerId');
  const token=sessionStorage.getItem('token');
  useEffect(() => {
    
    
    if (customerId) {
      axios.get(`${config.url}/bank/user/getdetails/${customerId}`,{
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
        .then((response) => {
          const data = response.data || {};
          setFormData({
            firstName: data.firstName || '',
            middleName: data.middleName || '',
            lastName: data.lastName || '',
            email: data.email || '',
            phoneNumber: data.phoneNumber || '',
            password: '',
            confirmPassword: '',
            dateOfBirth: data.dateOfBirth || '',
            gender: data.gender || '',
            adharNo: data.adharNo || '',
            panNo: data.panNo || '',
            accountType: data.accountType || '',
            tpin: ''
          });
          setOriginalData(data);
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
          toast.error('Failed to fetch customer data.');
        });
    }
  }, [customerId]);

  const handleUpdateEmailPhone = async () => {
    const { email, phoneNumber } = formData;

    try {
      const url = `${config.url}/bank/user/updateEmailPhone/${customerId}?email=${encodeURIComponent(email)}&phoneNumber=${encodeURIComponent(phoneNumber)}`;

      await axios.put(url, null, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
      toast.success('Email and/or phone number updated successfully!');
    } catch (error) {
      console.error('Failed to update email and/or phone number:', error);
      toast.error('Failed to update email and/or phone number.');
    }
  };

  const handleUpdatePassword = async () => {
    const { password } = formData;

    try {
      await axios.put(`${config.url}/bank/user/updatePassword/${customerId}`, password, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
      toast.success('Password updated successfully!');
    } catch (error) {
      console.error('Error updating password:', error);
      toast.error('Failed to update password.');
    }
  };

  const handleUpdateTpin = async () => {
    const { tpin } = formData;

    try {
      await axios.post(`${config.url}/bank/user/updatetpin/${customerId}?newTpin=${tpin}`, null, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
      toast.success('TPIN updated successfully!');
    } catch (error) {
      console.error('Error updating TPIN:', error);
      toast.error('Failed to update TPIN.');
    }
  };

  const handleVerifyAndUpdate = (action) => {
    setCurrentAction(action);
    setShowModal(true);
  };

  const handleVerificationSuccess = () => {
    if (currentAction === 'updateEmailPhone') {
      handleUpdateEmailPhone();
    } else if (currentAction === 'updatePassword') {
      handleUpdatePassword();
    } else if (currentAction === 'updateTpin') {
      handleUpdateTpin();
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const toggleConfirmPasswordVisibility = () => {
    setConfirmPasswordVisible(!confirmPasswordVisible);
  };

  const toggleTpinVisibility = () => {
    setTpinVisible(!tpinVisible);
  };

  return (
    <div className="customer-container">
      <div className="customer-card">
        <h2 className="customer-header">My Profile</h2>

        {/* Personal Details Section */}
        <div className="personal-details-section">
          <div className="form-group">
            <label htmlFor="firstName">First Name:</label>
            <input
              type="text"
              className="form-control"
              id="firstName"
              name="firstName"
              value={formData.firstName || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="middleName">Middle Name:</label>
            <input
              type="text"
              className="form-control"
              id="middleName"
              name="middleName"
              value={formData.middleName || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="lastName">Last Name:</label>
            <input
              type="text"
              className="form-control"
              id="lastName"
              name="lastName"
              value={formData.lastName || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="dateOfBirth">Date of Birth:</label>
            <input
              type="date"
              className="form-control"
              id="dateOfBirth"
              name="dateOfBirth"
              value={formData.dateOfBirth || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="gender">Gender:</label>
            <select
              className="form-control"
              id="gender"
              name="gender"
              value={formData.gender || ''}
              onChange={handleChange}
              readOnly
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="adharNo">Aadhaar No:</label>
            <input
              type="text"
              className="form-control"
              id="adharNo"
              name="adharNo"
              value={formData.adharNo || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="panNo">PAN No:</label>
            <input
              type="text"
              className="form-control"
              id="panNo"
              name="panNo"
              value={formData.panNo || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
          <div className="form-group">
            <label htmlFor="accountType">Account Type:</label>
            <input
              type="text"
              className="form-control"
              id="accountType"
              name="accountType"
              value={formData.accountType || ''}
              onChange={handleChange}
              readOnly
            />
          </div>
        </div>

        {/* Update Contact Details Section */}
        <div className="update-section three-column">
          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              value={formData.email || ''}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="phoneNumber">Phone Number:</label>
            <input
              type="tel"
              className="form-control"
              id="phoneNumber"
              name="phoneNumber"
              value={formData.phoneNumber || ''}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <button className="button-profile" onClick={() => handleVerifyAndUpdate('updateEmailPhone')}>Update Contact Details</button>
          </div>
        </div>

        {/* Update Password Section */}
        <div className="update-section three-column">
          <div className="form-group password-container">
            <label htmlFor="password">Password:</label>
            <input
              type={passwordVisible ? 'text' : 'password'}
              className="form-control"
              id="password"
              name="password"
              value={formData.password || ''}
              onChange={handleChange}
            />
            <span className="visibility-toggle" onClick={togglePasswordVisibility}>
              {passwordVisible ? <FaEyeSlash /> : <FaEye />}
            </span>
          </div>
          <div className="form-group password-container">
            <label htmlFor="confirmPassword">Confirm Password:</label>
            <input
              type={confirmPasswordVisible ? 'text' : 'password'}
              className="form-control"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword || ''}
              onChange={handleChange}
              required
            />
            <span className="visibility-toggle" onClick={toggleConfirmPasswordVisibility}>
              {confirmPasswordVisible ? <FaEyeSlash /> : <FaEye />}
            </span>
          </div>
          <div className="form-group">
            <button className="button-profile" onClick={() => handleVerifyAndUpdate('updatePassword')}>Update Password</button>
          </div>
        </div>

        {/* Update TPIN Section */}
        <div className="update-section two-column">
          <div className="form-group tpin-container">
            <label htmlFor="tpin">TPIN:</label>
            <input
              type={tpinVisible ? 'text' : 'password'}
              className="form-control"
              id="tpin"
              name="tpin"
              value={formData.tpin || ''}
              onChange={handleChange}
            />
            <span className="visibility-toggle" onClick={toggleTpinVisibility}>
              {tpinVisible ? <FaEyeSlash /> : <FaEye />}
            </span>
          </div>
          <div className="form-group">
            <button className="button-profile" onClick={() => handleVerifyAndUpdate('updateTpin')}>Update TPIN</button>
          </div>
        </div>

        {/* Verify Tpin Modal */}
        <VerifyTpinModal
          show={showModal}
          handleClose={() => setShowModal(false)}
          onVerify={handleVerificationSuccess}
        />
      </div>
    </div>
  );
};

export default CustomerForm;








