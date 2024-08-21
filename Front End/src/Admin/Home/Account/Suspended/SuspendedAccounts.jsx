import React, { useState, useEffect } from 'react';
import axios from 'axios';
import config from '../../../../config';

function SuspendedAccounts() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = sessionStorage.getItem('token'); // Retrieve the token from session storage

    axios.get(`${config.url}/bank/admin/suspended`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
      }
    })
    .then(response => {
      setAccounts(response.data);
      setLoading(false);
      console.log(response.data[0]?.accountNo); // Log accountNo, using optional chaining to avoid errors if data is empty
    })
    .catch(error => {
      console.error("There was an error fetching the suspended accounts!", error);
      setLoading(false);
    });
  }, []); // Empty dependency array ensures this runs only once

  const handleActivate = (accountNo) => {
    const token = sessionStorage.getItem('token'); // Retrieve the token from session storage

    axios.patch(`${config.url}/bank/admin/updateStatusActivated/${accountNo}`, {}, {
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
      <h2>Suspended Accounts</h2>
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

export default SuspendedAccounts;

