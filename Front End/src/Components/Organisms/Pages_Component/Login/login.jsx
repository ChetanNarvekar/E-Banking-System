import React, { useState, useEffect } from 'react';
import './login.scss';
import { FaUser, FaLock } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { bankLogo } from '../../../../Assets';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';
import config from '../../../../config';

const LoginForm = () => {
  const [emailOrPhone, setEmailOrPhone] = useState('');
  const [password, setPassword] = useState('');
  const [captchaInput, setCaptchaInput] = useState('');
  const [captcha, setCaptcha] = useState('');
  const navigate = useNavigate();

  const generateCaptcha = () => {
    const randomStr = Math.random().toString(36).substring(2, 7);
    setCaptcha(randomStr);
  };

  useEffect(() => {
    generateCaptcha();
  }, []);

  const OnClick = async () => {
    if (captchaInput !== captcha) {
      toast.error('Invalid CAPTCHA');
      generateCaptcha(); // regenerate CAPTCHA on failure
      return;
    }

    const body = {
      emailOrPhone,
      password,
    };

    try {
      const response = await axios.post(`${config.url}/bank/login`, body);
      const { customerId, email, role, phoneNumber, accountNo,jwtToken } = response.data;

      if (role === 'ROLE_ADMIN' || role === 'ROLE_SUPER_ADMIN') {
        sessionStorage.setItem('customerId', customerId);
        sessionStorage.setItem('email', email);
        sessionStorage.setItem('role', role);
        sessionStorage.setItem('phoneNumber', phoneNumber);
        sessionStorage.setItem('accountNo', accountNo);
        sessionStorage.setItem('token',jwtToken)

        toast.success('Login successful');
        navigate('/adminHome');
      } else {
        sessionStorage.setItem('customerId', customerId);
        sessionStorage.setItem('email', email);
        sessionStorage.setItem('role', role);
        sessionStorage.setItem('phoneNumber', phoneNumber);
        sessionStorage.setItem('accountNo', accountNo);
        sessionStorage.setItem('token',jwtToken);

        toast.success('Login successful');
        navigate('/myaccount');
      }
    } catch (error) {
      toast.error('Login failed');
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div className='l'>
      <div className='wrapper1 reduced-size'>
        <div className='logo1'>
          <img src={bankLogo} alt='Bank Logo' />
          <h4>JWT Bank</h4>
        </div>
        <form onSubmit={handleSubmit}>
          <h1>Login</h1>
          <div className='input-box'>
            <input
              type='text'
              placeholder='Username'
              value={emailOrPhone}
              onChange={(e) => setEmailOrPhone(e.target.value)}
              required
            />
            <FaUser className='icon' />
          </div>
          <div className='input-box'>
            <input
              type='password'
              placeholder='Password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <FaLock className='icon' />
          </div>
          <div className='captcha-container'>
            <div className='captcha-box'>
              <span className='captcha-text'>{captcha}</span>
            </div>
            <input
              type='text'
              placeholder='Enter CAPTCHA'
              value={captchaInput}
              onChange={(e) => setCaptchaInput(e.target.value)}
              required
            />
          </div>
          <div className='remember-forgot'>
            <label>
              <input type='checkbox' /> Remember Me
            </label>
            <a href='#'>Forgot Password</a>
          </div>
          <button onClick={OnClick} type='submit'>
            Login
          </button>
          <div className='register-link'>
            <p>
              Don't have an account? <Link to='/register'>Register</Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;