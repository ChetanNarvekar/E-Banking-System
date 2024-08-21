import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './style.scss'; // If you need additional custom styles

const AccountTypeTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get(`${config.url}/accType`)
      .then(response => {
        setData(response.data);
      })
      .catch(error => {
        console.error('Error fetching account types:', error);
      });
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
        <h2 className="text-center mb-4">Account Types</h2>
        <table className="table table-hover table-bordered">
          <thead className="thead-light">
            <tr>
              <th scope="col">Account Type Name</th>
              <th scope="col">Interest Rate (%)</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item) => (
              <tr key={item.accTypeId}>
                <td>{item.accTypeName}</td>
                <td>{item.interestRate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AccountTypeTable;
