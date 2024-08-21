
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import "./register.scss";
import { FaUser, FaLock } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import { bankLogo } from '../../../../Assets';
import config from '../../../../config';

const RegisterForm = () => {
    const [firstName, setFirstName] = useState('');
    const [middleName, setMiddleName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState(''); // New state for confirm password
    const [phoneNumber, setPhoneNumber] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState(new Date());
    const [gender, setGender] = useState('');
    const [adharNo, setAdharNo] = useState('');
    const [panNo, setPanNo] = useState('');
    const [tpin, setTpin] = useState('');
    const [accountType, setAccountType] = useState('');

    const genderOptions = ['MALE', 'FEMALE', 'TRANSGENDER'];
    const accountOptions = ['SAVING','CURRENT','MINOR'];
    const navigate = useNavigate();

    // Handler for dropdown change
    const handleChangeGender = (event) => {
        setGender(event.target.value);
    };

    const handleChangeAccountType = (event) => {
        setAccountType(event.target.value);
    };

    const OnClick = async () => {
        if (password !== confirmPassword) {
            alert('Passwords do not match!');
            return;
        }

        const body = {
            firstName, middleName, lastName, email, password, phoneNumber, dateOfBirth, gender, adharNo, panNo, tpin, accountType
        };
        console.log(body);
        try {
            const token = sessionStorage.getItem('token'); 
        
            const response = await axios.post(`${config.url}/bank/register`, body, {
                headers: {
                    'Authorization': `Bearer ${token}`, 
                    'Content-Type': 'application/json'
                }
            });
        
            console.log(response.data);
            navigate("/login");
        } catch (error) {
            console.error('Error creating customer:', error);
        }
        
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        OnClick();
    };

    return (
        <div className='login'>
            <div className='wrapper'>
                <div className='logo'>
                    <img src={bankLogo} alt="Bank Logo" />
                    <h4>JWT Bank</h4>
                </div>
                <form onSubmit={handleSubmit}>
                    <h1>Register</h1>
                    <div className='section'>
                        <div className='section-1'>
                            <div className='input-box'>
                                <label>First Name:</label>
                                <input type='text' placeholder='First name' value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Middle Name:</label>
                                <input type='text' placeholder='Middle Name' value={middleName} onChange={(e) => setMiddleName(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Last Name:</label>
                                <input type='text' placeholder='Last Name' value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Password:</label>
                                <input type='password' placeholder='Password' value={password} onChange={(e) => setPassword(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Confirm Password:</label>
                                <input type='password' placeholder='Confirm Password' value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Gender:</label>
                                <select id="gender" value={gender} onChange={handleChangeGender}>
                                    <option value="" disabled>Select your gender</option>
                                    {genderOptions.map(gender => (
                                        <option key={gender} value={gender}>{gender}</option>
                                    ))}
                                </select>
                            </div>
                            <div className='input-box'>
                                <label>Date Of Birth:</label>
                                <input type='date' placeholder='Date Of Birth' value={dateOfBirth} onChange={(e) => setDateOfBirth(e.target.value)} required />
                            </div>
                           
                        </div>
                        <div className='section-2'>
                            <div className='input-box'>
                                <label>Email:</label>
                                <input type='email' placeholder='Email' value={email} onChange={(e) => setEmail(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Phone Number:</label>
                                <input type='text' placeholder='Phone Number' value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Account Type:</label>
                                <select id="accountType" value={accountType} onChange={handleChangeAccountType}>
                                    <option value="" disabled>Select your account type</option>
                                    {accountOptions.map(accountType => (
                                        <option key={accountType} value={accountType}>{accountType}</option>
                                    ))}
                                </select>
                            </div>
                            <div className='input-box'>
                                <label>Transaction Pin:</label>
                                <input type='text' placeholder='T-Pin' value={tpin} onChange={(e) => setTpin(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Pan Number:</label>
                                <input type='text' placeholder='Pan Number' value={panNo} onChange={(e) => setPanNo(e.target.value)} required />
                            </div>
                            <div className='input-box'>
                                <label>Aadhar Number:</label>
                                <input type='text' placeholder='Aadhar number' value={adharNo} onChange={(e) => setAdharNo(e.target.value)} required />
                            </div>
                        </div>
                    </div>
                    <div className="remember-forgot">
                        <label>
                            <input type='checkbox' /> Remember Me
                        </label>
                        <a href="#">Forgot Password</a>
                    </div>
                    <button type='submit'>Create Account</button>
                    <div className="register-link">
                        <p>Already Have An Account? <Link to="/login">Login</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default RegisterForm;
