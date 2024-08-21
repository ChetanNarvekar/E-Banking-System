import React, { useState, useEffect } from 'react';
import axios from 'axios';
import config from '../../../../config';

function DeactivatedAccounts() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = sessionStorage.getItem('token'); // Retrieve the token from session storage

 useEffect(() => {
    

    axios.get(`${config.url}/bank/admin/getallaccountsdeactivated`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
      }
    })
    .then(response => {
      setAccounts(response.data);
      setLoading(false);
      console.log(response.data[0].accountNo); // Check if accountNo is correctly received
    })
    .catch(error => {
      console.error("There was an error fetching the deactivated accounts!", error);
      setLoading(false);
    });
  }, []); 

  const handleActivate = (accountNo) => {
   
  
    axios.patch(`${config.url}/bank/admin/updateStatusD/${accountNo}`, {}, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
      }
    })
    .then(response => {
      alert('Account activated successfully!');
      setAccounts(accounts.filter(account => account.accountNo !== accountNo));
    })
    .catch(error => {
      console.error("There was an error activating the account!", error);
    });
  };
  

  if (loading) return <p>Loading...</p>;

  return (
    <div style={{ backgroundColor: 'white' }}>
      <h2>Deactivated Accounts</h2>
      <div style={{ height: '71vh' }}>
      <table>
        <thead>
          <tr>
            <th>Account No</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {accounts.length > 0 ? (
            accounts.map(account => (
              <tr key={account.accountNo}> {/* Use accountNo as key */}
                <td>{account.accountNo}</td>
                <td>
                  <button onClick={() => handleActivate(account.accountNo)}>
                    Activate
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="2" style={{ textAlign: 'center' }}>No content available</td>
            </tr>
          )}
        </tbody>
      </table>
      </div>
    </div>
  );
}

export default DeactivatedAccounts;
