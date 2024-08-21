import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './style.scss'; // If you need additional custom styles
import config from '../../../../config';

const LoanTypeTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchLoanDetails = async () => {
        try {
            const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
            
            const response = await axios.get(`${config.url}/bank/admin/getallloandetails`, {
                headers: {
                    'Authorization': `Bearer ${token}`, // Add the token in the Authorization header
                    'Content-Type': 'application/json'
                }
            });

            setData(response.data);
            console.log(response.data); // Log the fetched data

        } catch (error) {
            console.error('Error fetching loan details:', error);
        }
    };

    fetchLoanDetails();
}, []);


  return (
    <div className="container-fluid" style={{
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      minHeight: '100vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    }}>
      <div className="card p-4" style={{ background: 'rgba(255, 255, 255, 0.85)', maxWidth: '800px', width: '100%' }}>
        <h2 className="text-center mb-4">Loan Types</h2>
        <table className="table table-hover table-bordered">
          <thead className="thead-light">
            <tr>
              <th scope="col">Loan Type Name</th>
              <th scope="col">Interest Rate (%)</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item) => (
              <tr key={item.loanName}>
                <td>{item.loanName}</td>
                <td>{item.interestRate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LoanTypeTable;
