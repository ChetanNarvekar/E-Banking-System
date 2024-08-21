import React, { useState, useEffect } from 'react';
import axios from 'axios';
import config from '../../../../config';
import './activatedAccounts.scss';

function ActivatedAccounts() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = sessionStorage.getItem('token');
  useEffect(() => {
    axios.get(`${config.url}/bank/admin/getallaccountsactiavted`, {
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
      console.error("There was an error fetching the activated accounts!", error);
    });
  }, []);
  const handleActivate = (accountNo) => {
    

    axios.patch(`${config.url}/bank/admin/updateStatusA/${accountNo}`, {}, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
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
      <h2>Active Accounts</h2>
      <table className='activated-accounts'>
        <thead>
          <tr>
            <th>Account No</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {accounts.map(account => (
            <tr key={account.accountNo}> {/* Use accountNo as key */}
              <td>{account.accountNo}</td>
              <td>
                <button onClick={() => handleActivate(account.accountNo)}>
                  Deactivate
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ActivatedAccounts;
